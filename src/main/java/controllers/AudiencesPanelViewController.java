package controllers;

import entity.AudienceEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 30.04.2016.
 */
public class AudiencesPanelViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;

    @FXML TableView<AudienceEntity> audienceTable;
    @FXML TableColumn<AudienceEntity, String> idColumn;
    @FXML TableColumn<AudienceEntity, String> typeColumn;
    @FXML TableColumn<AudienceEntity, Number> capacity;

    public AudiencesPanelViewController() {
    }

    public AudiencesPanelViewController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/audiences-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Аудитории");
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("audienceType"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        audienceTable.setItems(db.getAudienceData());
    }

    public void add(ActionEvent actionEvent) throws IOException {
        new AudienceEditorViewController(stage);
    }

    public void modify(ActionEvent actionEvent) throws IOException {
        int row =  audienceTable.getSelectionModel().getSelectedIndex();
        if (row == -1) {
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Аудитория не выбрана").show();
        } else {
            AudienceEntity audience = audienceTable.getSelectionModel().getSelectedItem();
            new AudienceEditorViewController(stage, audience);
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Данные успешно изменены").show();
        }
    }

    public void remove(ActionEvent actionEvent) {
        int row =  audienceTable.getSelectionModel().getSelectedIndex();
        if (row == -1) {
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Аудитория не выбрана").show();
        } else {
            AudienceEntity audience = audienceTable.getSelectionModel().getSelectedItem();
            db.removeAudience(audience);
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Запись успешно удалена").show();
        }
    }

    public void exit(ActionEvent actionEvent) {
        stage.close();
    }
}
