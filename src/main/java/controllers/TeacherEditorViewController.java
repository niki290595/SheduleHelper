package controllers;

import entity.TeacherEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 29.04.2016.
 */
public class TeacherEditorViewController implements Initializable {
    private static Stage stage;
    private static TeacherEntity teacher;
    private static Repository db = Repository.INSTANCE;

    @FXML TextField fio;
    @FXML TextField academicDegree;
    @FXML TextField position;
    @FXML TextField phone;

    public TeacherEditorViewController() {
    }

    public TeacherEditorViewController(Stage parentStage) throws IOException {
        this(parentStage, "Добавить", null);
    }


    public TeacherEditorViewController(Stage parentStage, TeacherEntity teacher) throws IOException {
        this(parentStage, "Изменить", teacher);
    }

    public TeacherEditorViewController(Stage parentStage, String title, TeacherEntity teacher) throws IOException {
        TeacherEditorViewController.teacher = teacher;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/teacher-editor.view.fxml"));
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (teacher == null)
            return;

        fio.setText(teacher.getFio());
        academicDegree.setText(teacher.getAcademicDegree());
        position.setText(teacher.getPosition());
        phone.setText(teacher.getPhone());
    }

    public void apply(ActionEvent actionEvent) {
        //todo запретить вставку пустых полей, exception
        String FIO = fio.getText();
        String academicDegree = this.academicDegree.getText();
        String position = this.position.getText();
        String phone = this.phone.getText();

        if (teacher == null) { //add
            db.addTeacher(FIO, academicDegree, position, phone);
            Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Запись добавлена").show();

            this.fio.setText("");
            this.academicDegree.setText("");
            this.position.setText("");
            this.phone.setText("");
        } else { //change
            db.editTeacher(teacher, FIO, academicDegree, position, phone);
            stage.close();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
