package orm;

import crypt.HashText;
import crypt.SaltGenerator;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

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

    private ObservableList<AudienceEntity> audienceData;
    private ObservableList<AudienceTypeEntity> audienceTypeData;
    private ObservableList<CategoryEntity> categoryData;
    private ObservableList<DirectionEntity> directionData;
    private ObservableMap<DirectionEntity, ObservableList<GroupEntity>> directionGroupData;
    private ObservableList<TeacherEntity> teacherData;
    private ObservableMap<Integer, TimeTableEntity> timeTableData;
    private ObservableList<UserEntity> userData;

    private ObservableList addCollection(Collection collection) {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(collection);
        Collections.sort(observableList);
        return observableList;
    }

    //region ACADEMIC PLAN ENTITY
    //endregion

    //region AUDIENCE ENTITY
    public ObservableList<AudienceEntity> getAudienceData() {
        return audienceData == null ? initAudienceData() : audienceData;
    }

    private ObservableList<AudienceEntity> initAudienceData() {
        audienceData = addCollection(dbHelper.getAudienceData());
        return audienceData;
    }

    public void addAudience(String id, AudienceTypeEntity type, int capacity) {
        AudienceEntity audience = dbHelper.insertAudience(id, type, capacity);
        audienceData.add(audience);
        Collections.sort(audienceData);
    }

    public void editAudience(AudienceEntity audience, String id, AudienceTypeEntity type, int capacity) {
        AudienceEntity newAudience = new AudienceEntity(id, type, capacity);
        dbHelper.alterAudience(audience, newAudience);
        audienceData.remove(audience);
        audienceData.add(newAudience);
        Collections.sort(audienceData);
    }


    public void removeAudience(AudienceEntity audience) {
        dbHelper.deleteAudience(audience);
        audienceData.remove(audience);
    }
    //endregion

    //region AUDIENCE TYPE ENTITY
    public ObservableList<AudienceTypeEntity> getAudienceTypeData() {
        return audienceTypeData == null ? initAudienceTypeData() : audienceTypeData;
    }

    private ObservableList<AudienceTypeEntity> initAudienceTypeData() {
        audienceTypeData = addCollection(dbHelper.getAudienceTypeData());
        return audienceTypeData;
    }

    public AudienceTypeEntity getAudienceType(AudienceEntity audience) {
        for (AudienceTypeEntity type : getAudienceTypeData()) {
            if (type.getName().equals(audience.getAudienceType().getName())) {
                return type;
            }
        }
        return null;
    }
    //endregion

    //region CATEGORY ENTITY
    public ObservableList<CategoryEntity> getCategoryData() {
        return categoryData == null ? initCategoryData() : categoryData;
    }

    private ObservableList<CategoryEntity> initCategoryData() {
        categoryData = addCollection(dbHelper.getCategoryData());
        return categoryData;
    }
    //endregion

    //region DIRECTION ENTITY
    public ObservableList<DirectionEntity> getDirectionData() {
        return directionData == null ? initDirectionData() : directionData;
    }

    private ObservableList<DirectionEntity> initDirectionData() {
        directionData = addCollection(dbHelper.getDirectionData());
        return directionData;
    }
    //endregion

    //region DISCIPLINE ENTITY
    //endregion

    //region DISCIPLINE TYPE ENTITY
    //endregion

    //region GROUP ENTITY
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

    //region MENTOR ENTITY
    //endregion

    //region NAVIGATOR ENTITY
    //endregion

    //region SCHEDULE ITEM ENTITY
    //endregion

    //region TEACHER ENTITY
    public ObservableList<TeacherEntity> getTeacherData() {
        return teacherData == null ? initTeacherData() : teacherData;
    }

    private ObservableList<TeacherEntity> initTeacherData() {
        teacherData = addCollection(dbHelper.getTeacherData());
        return teacherData;
    }

    public void addTeacher(String fio, String academicDegree, String position, String phone) {
        TeacherEntity teacher = dbHelper.addTeacher(fio, academicDegree, position, phone);
        teacherData.add(teacher);
        Collections.sort(teacherData);
    }

    public void alterTeacher(TeacherEntity teacher, String fio, String academicDegree, String position, String phone) {
        TeacherEntity newTeacher = new TeacherEntity(teacher.getId(), fio, academicDegree, position, phone);
        dbHelper.alterTeacher(teacher, newTeacher);
        teacherData.remove(teacher);
        teacherData.add(newTeacher);
        Collections.sort(teacherData);
    }

    public void removeTeacher(TeacherEntity teacher) {
        dbHelper.removeTeacher(teacher);
        teacherData.remove(teacher);
    }
    //endregion

    //region TIME TABLE ENTITY
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

    //region USER ENTITY
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
        UserEntity newUser = dbHelper.insertUser(login, hash, category, salt);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public UserEntity addUser(String login, CategoryEntity category) {
        UserEntity newUser = dbHelper.insertUser(login, category);
        userData.add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public void editUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        UserEntity alterUser = dbHelper.alterUser(user, newLogin, newCategory);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void editUser(UserEntity user, String pass) {
        String salt = SaltGenerator.generate();
        UserEntity alterUser = dbHelper.alterUser(user, HashText.getHash(pass, salt), salt);
        userData.remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void removeUser(UserEntity user) {
        dbHelper.deleteUser(user);
        userData.remove(user);
    }
    //endregion
}