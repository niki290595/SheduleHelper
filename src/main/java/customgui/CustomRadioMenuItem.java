package customgui;

import javafx.scene.control.RadioMenuItem;

/**
 * Created by User on 07.06.2016.
 */
public class CustomRadioMenuItem extends RadioMenuItem {
    Class cl;

    public CustomRadioMenuItem(String text, Class cl) {
        super(text);
        this.cl = cl;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }
}
