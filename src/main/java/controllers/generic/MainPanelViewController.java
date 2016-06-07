package controllers.generic;

import controllers.DialogController;
import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import other.ClassCollector;
import other.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static other.Session.user;

/**
 * Created by User on 07.06.2016.
 */
public class MainPanelViewController implements Initializable {
    private static Stage stage;
    private static UserEntity user;
    private static ClassCollector classCollector = ClassCollector.INSTANCE;

    @FXML Label status;
    @FXML Menu tablesMenu;

    public MainPanelViewController() {
    }

    public static void open(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(MainPanelViewController.class.getClassLoader().getResource("views/generic/main-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + Session.user.getLogin());
        stage.setScene(new Scene(root));
        MainPanelViewController.user = Session.user;
        parentStage.hide();
        stage.show();
        (new DialogController(stage, DialogController.Type.INFO))
                .setTitle("Уведомление")
                .setMsg("Добро пожаловать " + user.getLogin()).show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (Class aClass : classCollector.getClasses("entity")) {
                String des = classCollector.getDescription(aClass);
                if (des == null) continue;

                MenuItem item = new MenuItem(des);
                tablesMenu.getItems().add(item);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    public void add(ActionEvent actionEvent) {
    }

    public void edit(ActionEvent actionEvent) {
    }

    public void del(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
    }
}
