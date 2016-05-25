package controllers;

import entity.TimeTableEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by User on 30.04.2016.
 */
public class TimePanelViewController implements Initializable {
    private static Stage stage;
    private static Repository db = Repository.INSTANCE;

    @FXML TextField st1;
    @FXML TextField st2;
    @FXML TextField st3;
    @FXML TextField st4;
    @FXML TextField st5;
    @FXML TextField st6;
    @FXML TextField st7;
    @FXML TextField ft1;
    @FXML TextField ft2;
    @FXML TextField ft3;
    @FXML TextField ft4;
    @FXML TextField ft5;
    @FXML TextField ft6;
    @FXML TextField ft7;

    public TimePanelViewController() {
    }

    public TimePanelViewController(Stage parentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/time-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Время пар");
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Map<Integer, TimeTableEntity> time = db.getTimeTableData();
        for (Integer integer : time.keySet()) {
            TimeTableEntity item = time.get(integer);
            try {
                TextField textField = getBeginTextField(integer);
                textField.setText(item.timeBeginString());

                textField = getEndTextField(integer);
                textField.setText(item.timeEndString());

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private TextField getBeginTextField(Integer integer) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField("st" + integer);
        return (TextField) field.get(this);
    }

    private TextField getEndTextField(Integer integer) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getClass().getDeclaredField("ft" + integer);
        return (TextField) field.get(this);
    }


    public void apply(ActionEvent actionEvent) throws NoSuchFieldException, IllegalAccessException {
        for (Integer id = 1; id <= 7; id++) {
            String beginTime = getBeginTextField(id).getText();
            String endTime = getEndTextField(id).getText();

            if (beginTime.length() == 0 || endTime.length() == 0) {
                new DialogController(stage, DialogController.Type.INFO)
                        .setTitle("Ошибка")
                        .setMsg("Данные не ведены").show();
                return;
            }

            db.editTimeTable(id, beginTime, endTime);
        }
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
