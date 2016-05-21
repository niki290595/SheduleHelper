package controllers;

import entity.AudienceEntity;
import entity.AudienceTypeEntity;
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
 * Created by User on 30.04.2016.
 */
public class AudienceEditorViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;
    private static AudienceEntity audience;

    @FXML TextField num;
    @FXML ComboBox<AudienceTypeEntity> audienceType;
    @FXML TextField capacity;

    public AudienceEditorViewController() {
    }

    public AudienceEditorViewController(Stage parentStage) throws IOException {
        this(parentStage, "Добавить", null);
    }

    public AudienceEditorViewController(Stage parentStage, AudienceEntity audience) throws IOException {
        this(parentStage, "Изменить", audience);
    }

    public AudienceEditorViewController(Stage parentStage, String title, AudienceEntity audience) throws IOException {
        AudienceEditorViewController.audience = audience;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/audience-editor.view.fxml"));
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        audienceType.setItems(db.getAudienceTypeData());

        if (audience == null) return;

        num.setText(audience.getNum());
        capacity.setText(Integer.toString(audience.getCapacity()));
        audienceType.getSelectionModel().select(db.getAudienceType(audience));
    }

    public void apply(ActionEvent actionEvent) throws IOException {
        AudienceTypeEntity type = audienceType.getSelectionModel().getSelectedItem();
        String num = this.num.getText();
        int capacity = Integer.parseInt(this.capacity.getText());

        if (audience == null) {
            db.addAudience(num, type, capacity);
            this.num.setText("");
            this.capacity.setText("");
        } else {
            db.editAudience(audience, num, type, capacity);
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Уведомление")
                    .setMsg("Запись успешно изменена").show();
            stage.close();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
