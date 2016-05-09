package controllers;

import entity.DirectionEntity;
import entity.GroupEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 09.05.2016.
 */
public class GroupEditorViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;
    private static GroupEntity group;
    private static DirectionEntity direction;

    @FXML ComboBox<DirectionEntity> directions;
    @FXML TextField name;
    @FXML TextField count;

    public GroupEditorViewController() {
    }

    public GroupEditorViewController(Stage parentStage, String title, DirectionEntity direction, GroupEntity group) throws IOException {
        this.direction = direction;
        this.group = group;
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/group-editor.view.fxml"));
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public GroupEditorViewController(Stage parentStage) throws IOException {
        this(parentStage, "Добавить", null, null);
    }

    public GroupEditorViewController(Stage parentStage, DirectionEntity direction) throws IOException {
        this(parentStage, "Добавить", direction, null);
    }

    public GroupEditorViewController(Stage parentStage, GroupEntity group) throws IOException {
        this(parentStage, "Добавить", null, group);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directions.setItems(db.getDirectionData());
        if (direction != null) directions.getSelectionModel().select(direction);
        if (group != null) {
            directions.getSelectionModel().select(group.getDirection());
            name.setText(group.getName());
            count.setText(group.getStudentsNumber().toString());
        }
    }

    public void apply(ActionEvent actionEvent) {
        DirectionEntity direction = directions.getSelectionModel().getSelectedItem();
        String name = this.name.getText();
        int studentNumber = Integer.parseInt(count.getText());

        if (group != null) {
            db.editGroup(group, direction, name, studentNumber);
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Запись успешно изменена").show();
            stage.close();
        } else {
            db.addGroup(direction, name, studentNumber);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
