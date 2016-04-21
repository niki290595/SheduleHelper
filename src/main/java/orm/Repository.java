package orm;

import entity.UserEntity;
import javafx.collections.ObservableList;

/**
 * Created by User on 19.04.2016.
 */
public enum Repository {
    INSTANCE;

    //todo class Repository

    private ObservableList userData;

    public ObservableList getUserData() {
        return userData;
    }

    public UserEntity getUser(String login) {
        return null;
    }

    public void removeUser(UserEntity user) {

    }

    public void editUser(UserEntity user, String newPass) {
        
    }
}
