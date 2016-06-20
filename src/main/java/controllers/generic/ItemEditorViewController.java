package controllers.generic;

import controllers.DialogController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import other.ClassCollector;
import other.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static other.Session.user;

/**
 * Created by User on 19.06.2016.
 */
public class ItemEditorViewController implements Initializable {
    private static Stage stage;
    private static Object obj;
    private static Class aClass;
    private static ClassCollector classCollector = ClassCollector.INSTANCE;

    public ItemEditorViewController() {
    }

    public static void open(Stage stage) throws IOException {
        open(stage, null);
    }

    public static void open(Stage parentStage, Object obj) throws IOException {
        Parent root = FXMLLoader.load(MainPanelViewController.class.getClassLoader().getResource("views/generic/item-editor.view.fxml"));
        ItemEditorViewController.obj = obj;
        stage = new Stage();
        stage.setTitle(obj != null ? "Изменить " + obj : "Добавить " + classCollector.getDescription(aClass));
        stage.setScene(new Scene(root));
        parentStage.hide();
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void setClass(Class aClass) {
        ItemEditorViewController.aClass = aClass;
    }

    public void apply(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }
}
