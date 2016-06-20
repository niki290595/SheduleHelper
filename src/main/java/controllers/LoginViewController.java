package controllers;

import crypt.HashText;
import authorization.UserEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import orm.HibernateGenericDao;
import orm.Repository;
import other.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by User on 19.04.2016.
 */
public class LoginViewController implements Initializable {
    private static Stage stage;

    @FXML ComboBox<HibernateGenericDao.DBMS> schemeCBox;
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
        initSchemeCBox();
        loginCBox.setItems(db.getUserData());
    }

    private void initSchemeCBox() {
        List<HibernateGenericDao.DBMS> list = new ArrayList<>();
        for (HibernateGenericDao.DBMS dbms : HibernateGenericDao.DBMS.values()) {
            list.add(dbms);
        }
        schemeCBox.setItems(FXCollections.observableArrayList(list));
        schemeCBox.getSelectionModel().select(HibernateGenericDao.DBMS.MYSQL);
        HibernateGenericDao.buildSessionFactory(HibernateGenericDao.DBMS.MYSQL);

        schemeCBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HibernateGenericDao.DBMS>() {
            @Override
            public void changed(ObservableValue<? extends HibernateGenericDao.DBMS> observable, HibernateGenericDao.DBMS oldValue, HibernateGenericDao.DBMS newValue) {
                HibernateGenericDao.buildSessionFactory(newValue);
                loginCBox.setItems(db.getUserData());
            }
        });
    }

    public void registration(ActionEvent actionEvent) throws IOException {
        new RegistrationViewController(stage);
        loginCBox.setItems(db.getUserData());
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
            Session.user = user;
            switch (user.getCategory().categoryType()) {
                case ADMIN:
                    stage.hide();
                    new AdminViewController(stage);
                    break;
                case OWNER:
                case STAFF:
                    controllers.generic.MainPanelViewController.open(stage);
                    //new MainPanelViewController(stage);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Авторизация не пройдена, пароль введен неверно", ButtonType.OK).showAndWait();
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
