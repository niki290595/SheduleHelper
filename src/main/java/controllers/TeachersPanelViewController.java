package controllers;

import entity.CategoryEntity;
import entity.TeacherEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;
import other.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 29.04.2016.
 */
public class TeachersPanelViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;

    @FXML TableView<TeacherEntity> teachersTable;
    @FXML TableColumn<TeacherEntity, Number> idColumn;
    @FXML TableColumn<TeacherEntity, String> FIOColumn;
    @FXML TableColumn<TeacherEntity, String> academicDegreeColumn;
    @FXML TableColumn<TeacherEntity, String> positionColumn;
    @FXML TableColumn<TeacherEntity, String> phoneColumn;
    @FXML Button addBtn;
    @FXML Button editBtn;
    @FXML Button delBtn;


    public TeachersPanelViewController() {
    }

    public TeachersPanelViewController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/teachers-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Учителя");
        stage.setScene(new Scene(root, 1024, 768));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();

        addBtn.setDisable(!Session.haveRule());
        editBtn.setDisable(!Session.haveRule());
        delBtn.setDisable(!Session.haveRule());
    }

    private void initTable() {
        teachersTable.setItems(null);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FIOColumn.setCellValueFactory(cellData -> cellData.getValue().fioProperty());
        academicDegreeColumn.setCellValueFactory(new PropertyValueFactory<>("academicDegree"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        teachersTable.setItems(db.getTeacherData());
    }

    public void add(ActionEvent actionEvent) throws IOException {
        new TeacherEditorViewController(stage);
    }

    public void modify(ActionEvent actionEvent) throws IOException {
        int row =  teachersTable.getSelectionModel().getSelectedIndex();
        if (row == -1) {
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Учитель не выбран").show();
        } else {
            TeacherEntity teacher = teachersTable.getItems().get(row);
            new TeacherEditorViewController(stage, teacher);
        }
        initTable();
    }

    public void remove(ActionEvent actionEvent) throws IOException {
        int row =  teachersTable.getSelectionModel().getSelectedIndex();
        if (row == -1) {
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Учитель не выбран").show();
        } else {
            TeacherEntity teacher = teachersTable.getSelectionModel().getSelectedItem();
            db.removeTeacher(teacher);
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Запись успешно удалена").show();
        }
    }

    public void exit(ActionEvent actionEvent) {
        stage.close();
    }
}
