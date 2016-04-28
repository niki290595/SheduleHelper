package customgui;

import entity.GroupEntity;
import javafx.scene.control.MenuItem;

/**
 * Created by User on 28.04.2016.
 */
public class MenuItemGroup extends MenuItem {
    GroupEntity group;

    public MenuItemGroup(GroupEntity group) {
        super(group.getName(), null);
        this.group = group;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }
}
