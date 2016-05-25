package controllers;

import com.sun.jnlp.ApiDialog;
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
        if (num.length() == 0 || type == null || this.capacity.getText().length() == 0) {
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Данные не ведены").show();
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(this.capacity.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            new DialogController(stage, DialogController.Type.INFO)
                    .setTitle("Ошибка")
                    .setMsg("Данные некорректны").show();
            return;
        }

        if (audience == null) {
            audience = db.addAudience(num, type, capacity);
            this.num.setText("");
            this.capacity.setText("");
            stage.close();
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

    public void addAudienceType(ActionEvent actionEvent) {
        DialogController dialog = new DialogController(stage, DialogController.Type.INPUT);
        dialog.setTitle("Добавить тип")
                .setMsg("Введиите название")
                .setInput("").show();

        if (dialog.dialogResult().equals(ApiDialog.DialogResult.OK)) {
            String name = dialog.getResultString();

            AudienceTypeEntity type = db.addAudienceType(name);
            audienceType.getSelectionModel().select(type);
        }
    }

    public static AudienceEntity getAudience() {
        return audience;
    }
}
