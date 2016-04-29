package controllers;

import javafx.stage.Stage;

/**
 * Created by User on 25.04.2016.
 */
public class DialogController {

    public enum Type {
        INFO;
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

    public void show() {
        //todo show msg;
    }
}
