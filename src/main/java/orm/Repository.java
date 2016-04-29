package orm;

import crypt.HashText;
import crypt.SaltGenerator;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.Serializable;
import java.sql.Time;
import java.util.*;

/**
 * Created by User on 19.04.2016.
 */
public enum Repository {
    INSTANCE;

    private static DbHelper dbHelper;

    static {
        dbHelper = DbHelper.INSTANCE;
    }

    private ObservableList<UserEntity> userData;
    private ObservableList<CategoryEntity> categoryData;
    private ObservableMap<Integer, TimeTableEntity> timeTableData;
    private ObservableList<DirectionEntity> directionData;
    private ObservableMap<DirectionEntity, ObservableList<GroupEntity>> directionGroupData;

    private ObservableList addCollection(Collection collection) {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(collection);
        Collections.sort(observableList);
        return observableList;
    }

    //region USER_ENTITY
    public ObservableList<UserEntity> getUserData() {
        return userData == null ? initUserData() : userData;
    }

    private ObservableList<UserEntity> initUserData() {
        userData = addCollection(dbHelper.getUsersData());
        return userData;
    }

    public UserEntity getUser(String login) {
        for (UserEntity user : getUserData()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public UserEntity addUser(String login, String hash, CategoryEntity category, String salt) {
        UserEntity newUser = dbHelper.addUser(login, hash, category, salt);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public UserEntity addUser(String login, CategoryEntity category) {
        UserEntity newUser = dbHelper.addUser(login, category);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public void editUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        UserEntity alterUser = dbHelper.editUser(user, newLogin, newCategory);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void editUser(UserEntity user, String pass) {
        String salt = SaltGenerator.generate();
        UserEntity alterUser = dbHelper.editUser(user, HashText.getHash(pass, salt), salt);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void removeUser(UserEntity user) {
        dbHelper.removeUser(user);
        userData.remove(user);
    }
    //endregion

    //region CATEGORY_ENTITY
    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData == null ? initCategoryData() : categoryData;
    }

    private ObservableList<CategoryEntity> initCategoryData() {
        categoryData = addCollection(dbHelper.getCategoryData());
        return categoryData;
    }
    //endregion

    //region TIME_TABLE_ENTITY
    public Map<Integer, TimeTableEntity> getTimeTableData() {
        return timeTableData == null ? initTimeTableData() : timeTableData;
    }

    private Map<Integer, TimeTableEntity> initTimeTableData() {
        timeTableData = FXCollections.observableHashMap();
        Collection<TimeTableEntity> timeTableData = dbHelper.getTimeTableData();
        for (TimeTableEntity item : timeTableData) {
            this.timeTableData.put(item.getId(), item);
        }
        return this.timeTableData;
    }
    //endregion

    //region DIRECTION_ENTITY
    public ObservableList<DirectionEntity> getDirectionData() {
        return directionData == null ? initDirectionData() : directionData;
    }

    private ObservableList<DirectionEntity> initDirectionData() {
        directionData = addCollection(dbHelper.getDirectionData());
        return directionData;
    }
    //endregion

    //region GROUP_ENTITY
    public ObservableList<GroupEntity> getGroupData(DirectionEntity direction) {
        if (directionGroupData.get(direction) == null) {
            initGroupData(direction);
        }
        return directionGroupData.get(direction);
    }

    private void initGroupData(DirectionEntity direction) {
        ObservableList<GroupEntity> groupObservableList = addCollection(dbHelper.getGroupData());
        directionGroupData.put(direction, groupObservableList);
    }
    //endregion

    //region SCHEDULE_ENTITY
    //endregion

}