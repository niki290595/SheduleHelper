package orm;

import entity.CategoryEntity;
import entity.UserEntity;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by User on 19.04.2016.
 */
public enum Repository {
    INSTANCE;

    //todo class Repository

    private ObservableList userData;
    private ObservableList<CategoryEntity> categoryData;

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

    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData;
    }

    public void addUser(String login, String hash, CategoryEntity category, String salt) {

    }

    public void addUser(String text, CategoryEntity selectedItem) {

    }

    public void editUser(UserEntity user, String text, CategoryEntity selectedItem) {

    }
}
