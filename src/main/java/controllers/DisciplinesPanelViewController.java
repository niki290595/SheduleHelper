package controllers;

import com.sun.jnlp.ApiDialog.DialogResult;
import entity.DirectionEntity;
import entity.DisciplineEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 01.05.2016.
 */
public class DisciplinesPanelViewController implements Initializable{
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;
    private static DisciplineEntity lastSelectedDiscipline;

    @FXML ComboBox<DirectionEntity> directions;
    @FXML ListView<DisciplineEntity> noUseDisciplineList;
    @FXML ListView<DisciplineEntity> useDisciplineList;

    public DisciplinesPanelViewController() {
    }

    public DisciplinesPanelViewController(Stage parentStage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("views/disciplines-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Дисциплины");
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directions.setItems(db.getDirectionData());
        useDisciplineList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            DisciplinesPanelViewController.lastSelectedDiscipline = newValue;
        });
        noUseDisciplineList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            DisciplinesPanelViewController.lastSelectedDiscipline = newValue;
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

    public void deleteDirection(ActionEvent actionEvent) {
        DirectionEntity direction = directions.getSelectionModel().getSelectedItem();
        directions.getSelectionModel().selectFirst();
        db.removeDirection(direction);
    }

    public void addDiscipline(ActionEvent actionEvent) {
        DialogController dialog = new DialogController(stage, DialogController.Type.INPUT);
        dialog.setTitle("Новая дисциплина")
                .setMsg("Введите новую дисциплину");
        do {
            dialog.setInput("");
            dialog.show();
            if (dialog.dialogResult() == DialogResult.OK) {
                String name = dialog.getResultString();
                if(name.length() != 0) {
                    db.addDiscipline(name);
                }
            }
        } while(dialog.dialogResult() == DialogResult.OK);
    }

    public void editDiscipline(ActionEvent actionEvent) {
        if(lastSelectedDiscipline == null) return;

        DialogController dialog = new DialogController(stage, DialogController.Type.INPUT);
        dialog.setTitle("Изменить дисциплину")
                .setMsg("Введите название дисциплины")
                .setInput(lastSelectedDiscipline.getName()).show();
            if(dialog.dialogResult() == DialogResult.OK) {
                String disciplineName = dialog.getResultString();
                if (disciplineName != "") {
                    db.editDiscipline(lastUsedList(), lastSelectedDiscipline, disciplineName);
                    new DialogController(stage, DialogController.Type.INFO)
                            .setTitle("Уведомление")
                            .setMsg("Запись изменена").show();
                }
            }
    }

    private ObservableList<DisciplineEntity> lastUsedList() {
        return useDisciplineList.getItems().contains(lastSelectedDiscipline) ?
                useDisciplineList.getItems() :
                noUseDisciplineList.getItems();
    }

    public void deleteDiscipline(ActionEvent actionEvent) {
        if (lastSelectedDiscipline != null)
            db.removeDiscipline(lastSelectedDiscipline);
    }

    public void addToUseList(ActionEvent actionEvent) {
        DirectionEntity selectedDirection = directions.getSelectionModel().getSelectedItem();
        DisciplineEntity selectedDiscipline = noUseDisciplineList.getSelectionModel().getSelectedItem();
        db.addDisciplineToDirection(selectedDiscipline, selectedDirection);
        //this.selectFirstItemInList(this.useDisciplineList);
    }

    public void removeFromUseList(ActionEvent actionEvent) {
        DirectionEntity selectedDirection = directions.getSelectionModel().getSelectedItem();
        DisciplineEntity selectedDiscipline = noUseDisciplineList.getSelectionModel().getSelectedItem();
        db.removeDisciplineFromDirection(selectedDiscipline, selectedDirection);
        //this.selectFirstItemInList(this.noUseDisciplineList);
    }
}
