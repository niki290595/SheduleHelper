package controllers;

import crypt.HashText;
import crypt.SaltGenerator;
import entity.CategoryEntity;
import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 19.04.2016.
 */
public class RegistrationViewController implements Initializable {
    private static Stage stage;
    private static Repository db;

    public TextField loginTEdit;
    public PasswordField passTEdit;
    public PasswordField repeatPassTEdit;
    public Circle repeatPassCircle;
    public Circle loginCircle;

    public RegistrationViewController() {
    }

    public RegistrationViewController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/registration.view.fxml"));
        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(root, 400, 200));
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = Repository.INSTANCE;

        loginTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkLogin(newValue);
        });
        passTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
        repeatPassTEdit.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPass(passTEdit.getText(), repeatPassTEdit.getText());
        });
    }

    private void checkLogin(String login) {
        UserEntity user = db.getUser(login);
        loginCircle.setFill(user == null && login.length() != 0 ? Color.GREEN : Color.RED);
    }

    private void checkPass(String pass, String repeatPass) {
        repeatPassCircle.setFill(pass.equals(repeatPass) ? Color.GREEN : Color.RED);
    }

    public void save(ActionEvent actionEvent) {
        if (loginCircle.getFill().equals(Color.RED) || repeatPassCircle.getFill().equals(Color.RED)) {
            new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные", ButtonType.OK).showAndWait();
            return;
        }

        String login = loginTEdit.getText();
        String pass = passTEdit.getText();
        CategoryEntity category = null;
        for (CategoryEntity categoryEntity : db.getCategoryData()) {
            if (db.getUserData().size() == 0) {
                if (categoryEntity.categoryType().equals(CategoryEntity.CategoryType.ADMIN)) {
                    category = categoryEntity;
                    break;
                }
            } else if (categoryEntity.categoryType().equals(CategoryEntity.CategoryType.STAFF)) {
                category = categoryEntity;
                break;
            }
        }
        String salt = SaltGenerator.generate();

        db.addUser(login, HashText.getHash(pass, salt), category, salt);
        new Alert(Alert.AlertType.INFORMATION, "Пользователь создан", ButtonType.OK).showAndWait();
        stage.hide();
    }

    public void cancel(ActionEvent actionEvent) {
        stage.hide();
    }
}