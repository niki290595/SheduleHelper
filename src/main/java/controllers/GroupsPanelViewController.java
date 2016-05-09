package controllers;

import com.sun.jnlp.ApiDialog.DialogResult;
import entity.DirectionEntity;
import entity.GroupEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 09.05.2016.
 */
public class GroupsPanelViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;

    @FXML ListView<DirectionEntity> directions;
    @FXML ListView<GroupEntity> groups;

    public GroupsPanelViewController() {
    }

    public GroupsPanelViewController(Stage parentStage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("views/groups-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Группы");
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directions.setItems(db.getDirectionData());
        directions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DirectionEntity>() {
            @Override
            public void changed(ObservableValue<? extends DirectionEntity> observable, DirectionEntity oldValue, DirectionEntity newValue) {
                if (newValue != null)
                    groups.setItems(db.getGroupData(newValue));
            }
        });
    }

    public void addDirection(ActionEvent actionEvent) {
        DialogController dialog = new DialogController(stage, DialogController.Type.INPUT);
        dialog.setTitle("Новое направление")
                .setMsg("Введите новое направление").show();
        if (dialog.dialogResult() == DialogResult.OK) {
            String directionName = dialog.getResultString();
            DirectionEntity newDirection = db.addDirection(directionName);
            directions.getSelectionModel().select(newDirection);
        }
    }

    public void editDirection(ActionEvent actionEvent) {
        DirectionEntity direction = directions.getSelectionModel().getSelectedItem();
        DialogController dialog = new DialogController(stage, DialogController.Type.INPUT);
        dialog.setTitle("Новое направление")
                .setMsg("Введите новое направление")
                .setInput(direction.getName()).show();
        if(dialog.dialogResult() == DialogResult.OK) {
            String directionName = dialog.getResultString();
            direction = db.editDirection(direction, directionName);
            directions.getSelectionModel().select(direction);
        }
    }

    public void delDirection(ActionEvent actionEvent) {
        DirectionEntity direction = directions.getSelectionModel().getSelectedItem();
        directions.getSelectionModel().selectFirst();
        db.removeDirection(direction);
    }

    public void addGroup(ActionEvent actionEvent) {
        DirectionEntity direction = directions.getSelectionModel().getSelectedItem();
        new GroupEditorViewController(stage, direction);
    }

    public void editGroup(ActionEvent actionEvent) {
        GroupEntity group = groups.getSelectionModel().getSelectedItem();
        if (group == null) return;
        new GroupEditorViewController(stage, group);
    }

    public void delGroup(ActionEvent actionEvent) {
        GroupEntity group = groups.getSelectionModel().getSelectedItem();
        db.removeGroup(group);
        new DialogController(stage, DialogController.Type.INFO)
                .setTitle("Уведомление")
                .setInput("Данные успешно удалены").show();
    }
}
