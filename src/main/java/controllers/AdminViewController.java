package controllers;

import authorization.UserEntity;
import controllers.generic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import orm.Repository;
import other.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by User on 19.04.2016.
 */
public class AdminViewController implements Initializable {
    private static Stage stage;
    private static Repository db;

    static {
        db = Repository.INSTANCE;
    }

    public TableView<UserEntity> usersTable;
    public TableColumn<UserEntity, String> loginColumn;
    public TableColumn<UserEntity, String> categoryColumn;
    public TableColumn<UserEntity, Date> dateCreationColumn;
    public TableColumn<UserEntity, Date> dateModificationColumn;

    public AdminViewController() {}

    public AdminViewController(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/admin.view.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + Session.user.getLogin());
        stage.setScene(new Scene(root, 400, 200));
        AdminViewController.stage = stage;
        stage.show();
        //AdminController.login = login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        dateModificationColumn.setCellValueFactory(new PropertyValueFactory<>("dateModification"));
        usersTable.setItems(db.getUserData());
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        new UserEditorViewController(stage);
    }

    public void editUser(ActionEvent actionEvent) throws IOException {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            new UserEditorViewController(stage, user);
        }
    }

    public void removeUser(ActionEvent actionEvent) {
        UserEntity user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            db.removeUser(user);
        }
    }

    public void openMainPanel(ActionEvent actionEvent) throws IOException {
        controllers.generic.MainPanelViewController.open(stage);
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}