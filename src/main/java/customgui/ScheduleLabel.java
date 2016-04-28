package customgui;

import javafx.scene.control.Label;

/**
 * Created by User on 28.04.2016.
 */
public class ScheduleLabel extends Label {
    private int dayOfWeek;
    private int idTime;

    public ScheduleLabel(String text, int dayOfWeek, int idTime) {
        super(text);
        this.dayOfWeek = dayOfWeek;
        this.idTime = idTime;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getIdTime() {
        return idTime;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }
}
