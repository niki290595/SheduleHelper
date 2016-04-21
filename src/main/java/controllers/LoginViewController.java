package controllers;

import crypt.HashText;
import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 19.04.2016.
 */
public class LoginViewController implements Initializable {
    private static Stage stage;
    @FXML ComboBox<UserEntity> loginCBox;
    @FXML PasswordField passTextField;

    private static Repository db;

    static {
        db = Repository.INSTANCE;
    }

    public LoginViewController() {
    }

    public LoginViewController(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.view.fxml"));
        stage = new Stage();
        stage.setTitle("Вход");
        stage.setScene(new Scene(root, 400, 200));
        LoginViewController.stage = stage;
        stage.show();
    }


    public void initialize(URL location, ResourceBundle resources) {
        loginCBox.setItems(db.getUserData());
    }

    public void registration(ActionEvent actionEvent) {
        new RegistrationViewController(stage);
    }

    public void enter(ActionEvent actionEvent) throws IOException {
        UserEntity user = loginCBox.getSelectionModel().getSelectedItem();
        String pass = passTextField.getText();

        if (pass.length() == 0 && user.getPass() == null) {
            new CreatePassViewController(stage, loginCBox.getSelectionModel().getSelectedItem());
            pass = CreatePassViewController.getPass();
            user = db.getUser(user.getLogin());
        }

        tryAuthorization(user, pass);
    }

    private void tryAuthorization(UserEntity user, String pass) throws IOException {
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Пользователь не выбран", ButtonType.OK).showAndWait();
            return;
        }

        if (user.getPass().equals(HashText.getHash(pass, user.getSalt()))) {
            new Alert(Alert.AlertType.INFORMATION, "Добро пожаловать " + user.getLogin(), ButtonType.OK).showAndWait();

            switch (user.getCategory().categoryType()) {
                case ADMIN:
                    stage.hide();
                    new AdminViewController(stage, user.getLogin());
                    break;
                case OWNER:
                case STAFF:
                    new Alert(Alert.AlertType.INFORMATION, "Работа программы завершена", ButtonType.OK).showAndWait();
                    System.exit(0);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Авторизация не пройдена, пароль введен неверно", ButtonType.OK).showAndWait();
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
