package controllers;

import com.sun.jnlp.ApiDialog.DialogResult;
import javafx.stage.Stage;

/**
 * Created by User on 25.04.2016.
 */
public class DialogController {

    public enum Type {
        INFO,
        INPUT;
    }

    private String title;
    private String msg;

    public DialogController(Stage parentStage, Type type) {

    }

    public String getTitle() {
        return title;
    }

    public DialogController setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DialogController setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public DialogController show() {
        //todo show msg;
        return this;
    }

    public DialogResult dialogResult() {
        return null;
    }

    public String getResultString() {
        return null;
    }

    public DialogController setInput(String input) {
        return this;
    }
}
