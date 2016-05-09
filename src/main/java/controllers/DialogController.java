package controllers;

import com.sun.jnlp.ApiDialog.DialogResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 25.04.2016.
 */
public class DialogController implements Initializable {

    @FXML TextField inputTextField;
    @FXML Label msgLabel;

    public enum Type {
        INFO,
        INPUT;
    }

    //private static DialogController currDialogParam;
    //private DialogController dialogParam;
    private static String title;
    private static String msg;
    private static String input;
    private static Type type;
    private static Stage stage;
    private static Stage parentStage;
    private static DialogResult result;

    public DialogController() {
    }

    public DialogController(Stage parentStage, Type type) {
        DialogController.type = type;
        DialogController.parentStage = parentStage;
        title = null;
        msg = null;
        input = null;
        result = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msgLabel.setText(msg);
        if (input != null) inputTextField.setText(input);
    }

    public String getTitle() {
        return title;
    }

    public DialogController setTitle(String title) {
        DialogController.title = title;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DialogController setMsg(String msg) {
        DialogController.msg = msg;
        return this;
    }

    public DialogController show() {
        Parent root = null;
            try {
                if (type.equals(Type.INFO))
                    root = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/info-dialog.view.fxml"));
                if (type.equals(Type.INPUT))
                    root = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/input-dialog.view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (root == null) return this;

        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return this;
    }

    public DialogResult dialogResult() {
        return DialogController.result;
    }

    public String getResultString() {
        return DialogController.input;
    }

    public DialogController setInput(String input) {
        DialogController.input = input;
        return this;
    }

    public void apply(ActionEvent actionEvent) {
        input = inputTextField.getText();
        result = DialogResult.OK;
        stage.close();
    }

    public void close(ActionEvent actionEvent) {
        DialogController.result = DialogResult.CANCEL;
        DialogController.stage.close();
    }
}
