package orm;

import authorization.CategoryEntity;
import authorization.UserEntity;
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

    private ObservableList<AcademicPlanEntity> academicPlanData;
    private ObservableList<AudienceEntity> audienceData;
    private ObservableList<AudienceTypeEntity> audienceTypeData;
    private ObservableList<CategoryEntity> categoryData;
    private ObservableList<DirectionEntity> directionData;
    private ObservableMap<DirectionEntity, ObservableList<GroupEntity>> directionGroupData;
    private ObservableMap<DirectionEntity, ObservableList<DisciplineEntity>> directionDisciplineData;
    private ObservableList<MentorEntity> mentorData;
    private ObservableList<NavigatorEntity> navigatorData;
    private ObservableList<GroupEntity> groupData;
    private ObservableList<DisciplineEntity> disciplineData;
    private ObservableList<DisciplineTypeEntity> disciplineTypeData;
    private ObservableList<ScheduleItemEntity> scheduleItemData;
    private ObservableList<DisciplineEntity> noUseDisciplineData;
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
    public ObservableList<AcademicPlanEntity> getAcademicPlanData() {
        return academicPlanData == null ? initAcademicPlanData() : academicPlanData;
    }

    private ObservableList<AcademicPlanEntity> initAcademicPlanData() {
        academicPlanData = addCollection(dbHelper.getAcademicPlanData());
        return academicPlanData;
    }


    public ObservableList<DisciplineEntity> getDisciplineData(DirectionEntity direction) {
        if (direction == null) return null;

        return directionDisciplineData == null ||
                directionDisciplineData.get(direction) == null ?
                initDisciplineData(direction) : directionDisciplineData.get(direction);
    }

    private ObservableList<DisciplineEntity> initDisciplineData(DirectionEntity direction) {
        if (directionDisciplineData == null)
            directionDisciplineData = FXCollections.observableHashMap();
        ObservableList<DisciplineEntity> disciplines = FXCollections.observableArrayList();

        for (AcademicPlanEntity academicPlanEntity : getAcademicPlanData()) {
            if (academicPlanEntity.getDirection().equals(direction))
                disciplines.add(academicPlanEntity.getDiscipline());
        }
        directionDisciplineData.put(direction, disciplines);
        return disciplines;
    }

    public ObservableList<DisciplineEntity> getNoUseDisciplineData(DirectionEntity direction) {
        if(noUseDisciplineData == null) {
            noUseDisciplineData = FXCollections.observableArrayList();
        }

        this.noUseDisciplineData.clear();
        this.noUseDisciplineData.addAll(getDisciplineData());
        if(direction != null) {
            this.noUseDisciplineData.removeAll(directionDisciplineData.get(direction));
        }

        return this.noUseDisciplineData;
    }
    //endregion

    //region AUDIENCE ENTITY
    public ObservableList<AudienceEntity> getAudienceData() {
        return audienceData == null ? initAudienceData() : audienceData;
    }

    private ObservableList<AudienceEntity> initAudienceData() {
        audienceData = addCollection(dbHelper.getAudienceData());
        return audienceData;
    }

    public AudienceEntity addAudience(String num, AudienceTypeEntity type, int capacity) {
        AudienceEntity audience = dbHelper.insertAudience(num, type, capacity);
        getAudienceData().add(audience);
        Collections.sort(audienceData);
        return audience;
    }

    public void editAudience(AudienceEntity audience, String num, AudienceTypeEntity type, int capacity) {
        AudienceEntity newAudience = dbHelper.alterAudience(audience, num, type, capacity);
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

    public AudienceTypeEntity addAudienceType(String name) {
        AudienceTypeEntity audienceType = dbHelper.insertAudienceType(name);
        getAudienceTypeData().add(audienceType);
        Collections.sort(audienceTypeData);
        return audienceType;
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

    public DirectionEntity addDirection(String name) {
        DirectionEntity newDirection = dbHelper.insertDirection(name);
        directionData.add(newDirection);
        Collections.sort(directionData);
        return newDirection;
    }

    public DirectionEntity editDirection(DirectionEntity direction, String directionName) {
        DirectionEntity alterDirection = dbHelper.alterDirection(direction, directionName);
        directionData.remove(direction);
        directionData.add(alterDirection);
        Collections.sort(directionData);
        return alterDirection;
    }

    public void removeDirection(DirectionEntity direction) {
        dbHelper.deleteDirection(direction);
        directionData.remove(direction);
    }
    //endregion

    //region DISCIPLINE ENTITY
    public ObservableList<DisciplineEntity> getDisciplineData() {
        return disciplineData == null ? initDisciplineData() : disciplineData;
    }

    private ObservableList<DisciplineEntity> initDisciplineData() {
        disciplineData = addCollection(dbHelper.getDisciplineData());
        return disciplineData;
    }

    public DisciplineEntity addDiscipline(String name) {
        DisciplineEntity discipline = dbHelper.insertDiscipline(name);
        getDisciplineData().add(discipline);
        Collections.sort(disciplineData);
        if (noUseDisciplineData != null) {
            noUseDisciplineData.add(discipline);
            Collections.sort(noUseDisciplineData);
        }
        return discipline;
    }

    public void addDisciplineToDirection(DisciplineEntity discipline, DirectionEntity direction) {
        dbHelper.addDiscipline(discipline, direction);
        if (directionDisciplineData.get(direction) == null)
            directionDisciplineData.put(direction, getDisciplineData(direction));
        directionDisciplineData.get(direction).add(discipline);
        if (noUseDisciplineData != null) noUseDisciplineData.remove(discipline);
        Collections.sort(directionDisciplineData.get(direction));
    }

    public DisciplineEntity editDiscipline(ObservableList<DisciplineEntity> disciplineList, DisciplineEntity discipline, String newName) {
        DisciplineEntity newDiscipline = dbHelper.alterDiscipline(discipline, newName);
        this.disciplineData.remove(discipline);
        this.disciplineData.add(newDiscipline);
        disciplineList.remove(discipline);
        disciplineList.add(newDiscipline);
        this.directionDisciplineData.keySet().stream().filter((direction) -> {
            return ((ObservableList)this.directionDisciplineData.get(direction)).contains(discipline);
        }).forEach((direction) -> {
            ((ObservableList)this.directionDisciplineData.get(direction)).remove(discipline);
            ((ObservableList)this.directionDisciplineData.get(direction)).add(newDiscipline);
        });
        Collections.sort(this.disciplineData);
        Collections.sort(disciplineList);
        return discipline;
    }

    public void removeDiscipline(DisciplineEntity discipline) {
        dbHelper.deleteDiscipline(discipline);
        this.disciplineData.remove(discipline);
        this.noUseDisciplineData.remove(discipline);
        this.directionDisciplineData.keySet().stream().filter((direction) -> {
            return this.directionDisciplineData.get(direction) != null;
        }).filter((direction) -> {
            return ((ObservableList)this.directionDisciplineData.get(direction)).contains(discipline);
        }).forEach((direction) -> {
            ((ObservableList)this.directionDisciplineData.get(direction)).remove(discipline);
        });
    }

    public void removeDisciplineFromDirection(DisciplineEntity discipline, DirectionEntity direction) {
        dbHelper.deleteDiscipline(discipline, direction);
        directionDisciplineData.get(direction).remove(discipline);
        noUseDisciplineData.add(discipline);
        Collections.sort(noUseDisciplineData);
    }
    //endregion

    //region DISCIPLINE TYPE ENTITY
    public ObservableList<DisciplineTypeEntity> getDisciplineTypeData() {
        return disciplineTypeData == null ? initDisciplineTypeData() : disciplineTypeData;
    }

    private ObservableList<DisciplineTypeEntity> initDisciplineTypeData() {
        disciplineTypeData = addCollection(dbHelper.getDisciplineTypeData());
        return disciplineTypeData;
    }

    public DisciplineTypeEntity addDisciplineType(String name) {
        DisciplineTypeEntity newDisciplineType = dbHelper.insertDisciplineType(name);
        getDisciplineTypeData().add(newDisciplineType);
        Collections.sort(disciplineTypeData);
        return newDisciplineType;
    }
    //endregion

    //region GROUP ENTITY
    public ObservableList<GroupEntity> getGroupData() {
        return groupData == null ? initGroupData() : groupData;
    }

    private ObservableList<GroupEntity> initGroupData() {
        groupData = addCollection(dbHelper.getGroupData());
        return groupData;
    }

    public ObservableList<GroupEntity> getGroupData(DirectionEntity direction) {
        if (directionGroupData == null || directionGroupData.get(direction) == null) {
            initGroupData(direction);
        }
        return directionGroupData.get(direction);
    }

    public String getGroupData(NavigatorEntity navigator) {
        List<GroupEntity> list = new ArrayList<>();
        for (ScheduleItemEntity item : getScheduleItemData()) {
            if (item.getNavigator().equals(navigator)) {
                list.add(item.getGroup());
            }
        }

        String res = "";
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i);
            if (i < list.size() - 1) {
                res += ", ";
            }
        }
        return res;
    }

    private ObservableList<GroupEntity> initGroupData(DirectionEntity direction) {
        if (directionGroupData == null)
            directionGroupData = FXCollections.observableHashMap();

        ObservableList<GroupEntity> groups = FXCollections.observableArrayList();
        for (GroupEntity group : getGroupData()) {
            if (group.getDirection().equals(direction)) groups.add(group);
        }
        directionGroupData.put(direction, groups);
        return groups;
    }

    public GroupEntity addGroup(DirectionEntity direction, String name, int studentNumber) {
        GroupEntity group = dbHelper.insertGroup(direction, name, studentNumber);
        directionGroupData.get(group.getDirection()).add(group);
        Collections.sort(directionGroupData.get(group.getDirection()));
        return group;
    }

    public void editGroup(GroupEntity group, DirectionEntity direction, String name, int studentNumber) {
        GroupEntity newGroup = dbHelper.alterGroup(group, direction, name, studentNumber);
        directionGroupData.get(direction).remove(group);
        directionGroupData.get(direction).add(newGroup);
        Collections.sort(directionGroupData.get(direction));

    }

    public void removeGroup(GroupEntity group) {
        dbHelper.deleteGroup(group);
        directionGroupData.get(group.getDirection()).remove(group);
    }
    //endregion

    //region MENTOR ENTITY
    private ObservableList<MentorEntity> getMentorData() {
        return mentorData == null ? initMentorData() : mentorData;
    }

    private ObservableList<MentorEntity> initMentorData() {
        return addCollection(dbHelper.getMentorData());
    }

    private MentorEntity getMentor(DisciplineEntity discipline, DisciplineTypeEntity type, TeacherEntity teacher) {
        for (MentorEntity mentor : getMentorData()) {
            if (mentor.getTeacher().equals(teacher) &&
                    mentor.getDiscipline().equals(discipline) &&
                    mentor.getDisciplineType().equals(type))
                return mentor;
        }
        return null;
    }

    private MentorEntity addMentor(DisciplineEntity discipline, DisciplineTypeEntity type, TeacherEntity teacher) {
        MentorEntity mentor = dbHelper.insertMentor(discipline, type, teacher);
        getMentorData().add(mentor);
        return mentor;
    }
    //endregion

    //region NAVIGATOR ENTITY
    private ObservableList<NavigatorEntity> getNavigatorData() {
        return navigatorData == null ? initNavigatorData() : navigatorData;
    }

    private ObservableList<NavigatorEntity> initNavigatorData() {
        return addCollection(dbHelper.getNavigatorData());
    }

    public NavigatorEntity getNavigator(int dayOfWeek, TimeTableEntity time, int weekOdd, AudienceEntity audience) {
        for (NavigatorEntity navigator : initNavigatorData()) {
            if (navigator.getDayOfWeek().equals(dayOfWeek) &&
                    navigator.getTime().equals(time) &&
                    navigator.getWeekOdd().equals(weekOdd) &&
                    navigator.getAudience().equals(audience))
                return navigator;
        }
        return null;
    }

    private NavigatorEntity addNavigator(int dayOfWeek, TimeTableEntity time, int weekOdd, AudienceEntity audience, MentorEntity mentor) {
        NavigatorEntity navigator = dbHelper.insertNavigator(dayOfWeek, time, weekOdd, audience, mentor);
        getNavigatorData().add(navigator);
        return navigator;
    }

    private void removeNavigator(NavigatorEntity navigator) {
        dbHelper.deleteNavigator(navigator);
        getNavigatorData().remove(navigator);
    }
    //endregion

    //region SCHEDULE ITEM ENTITY
    public ObservableList<ScheduleItemEntity> getScheduleItemData() {
        if (scheduleItemData == null) {
            initScheduleItemData();
        }
        return scheduleItemData;
    }

    private ObservableList<ScheduleItemEntity> initScheduleItemData() {
        scheduleItemData = addCollection(dbHelper.getScheduleItemData());
        return scheduleItemData;
    }

    public ScheduleItemEntity[][] getScheduleItemData(Object obj, int weekOdd) {
        if (obj instanceof TeacherEntity)
            return getScheduleItemData((TeacherEntity) obj, weekOdd);
        else if (obj instanceof AudienceEntity)
            return getScheduleItemData((AudienceEntity) obj, weekOdd);
        else if (obj instanceof GroupEntity)
            return getScheduleItemData((GroupEntity) obj, weekOdd);
        return null;
    }

    public ScheduleItemEntity[][] getScheduleItemData(AudienceEntity audience, int weekOdd) {
        ScheduleItemEntity[][] res = new ScheduleItemEntity[6][7];
        for (ScheduleItemEntity item : initScheduleItemData()) {
            if (item.getNavigator().getAudience().equals(audience) &&
                        item.getNavigator().getWeekOdd().equals(weekOdd)) {
                res[item.getNavigator().getDayOfWeek() - 1][item.getNavigator().getTime().getId() - 1] = item;
            }
        }
        return res;
    }

    public ScheduleItemEntity[][] getScheduleItemData(TeacherEntity teacher, int weekOdd) {
        ScheduleItemEntity[][] res = new ScheduleItemEntity[6][7];
        for (ScheduleItemEntity item : initScheduleItemData()) {
            if (item.getNavigator().getMentor().getTeacher().equals(teacher) &&
                    item.getNavigator().getWeekOdd().equals(weekOdd)) {
                res[item.getNavigator().getDayOfWeek() - 1][item.getNavigator().getTime().getId() - 1] = item;
            }
        }
        return res;
    }

    public ScheduleItemEntity[][] getScheduleItemData(GroupEntity group, int weekOdd) {
        ScheduleItemEntity[][] res = new ScheduleItemEntity[6][7];
        for (ScheduleItemEntity item : initScheduleItemData()) {
            if (item.getGroup().equals(group) &&
                    item.getNavigator().getWeekOdd().equals(weekOdd)) {
                res[item.getNavigator().getDayOfWeek() - 1][item.getNavigator().getTime().getId() - 1] = item;
            }
        }
        return res;
    }

    public ScheduleItemEntity addScheduleItem(int dayOfWeek, TimeTableEntity time, int weekOdd, AudienceEntity audience, DisciplineEntity discipline, DisciplineTypeEntity type, GroupEntity group, TeacherEntity teacher) {
        NavigatorEntity navigator = getNavigator(dayOfWeek, time, weekOdd, audience);
        MentorEntity mentor = getMentor(discipline, type, teacher);

        if (mentor == null)
            mentor = addMentor(discipline, type, teacher);

        if (navigator != null && !navigator.getMentor().equals(mentor)) {
            removeNavigator(navigator);
            navigator = null;
        }

        if (navigator == null) {
            navigator = addNavigator(dayOfWeek, time, weekOdd, audience, mentor);
            navigator = getNavigator(dayOfWeek, time, weekOdd, audience);
        }

        return addScheduleItem(navigator, group);
    }

    private ScheduleItemEntity getScheduleItem(NavigatorEntity navigator) {
        for (ScheduleItemEntity item : getScheduleItemData()) {
            if (item.getNavigator().equals(navigator)) return item;
        }
        return null;
    }

    private ScheduleItemEntity addScheduleItem(NavigatorEntity navigator, GroupEntity group) {
        ScheduleItemEntity item = dbHelper.insertScheduleItem(navigator, group);
        getScheduleItemData().add(item);
        return item;
    }

    public void removeScheduleItem(ScheduleItemEntity item) { //, GroupEntity obj) {
        dbHelper.deleteScheduleItem(item);
        scheduleItemData.remove(item);
    }
    //endregion

    //region TEACHER ENTITY
    public ObservableList<TeacherEntity> getTeacherData() {
        return teacherData == null ? initTeacherData() : teacherData;
    }

    private ObservableList<TeacherEntity> initTeacherData() {
        teacherData = addCollection(dbHelper.getTeacherData());
        return teacherData;
    }

    public TeacherEntity addTeacher(String fio, String academicDegree, String position, String phone) {
        TeacherEntity teacher = dbHelper.addTeacher(fio, academicDegree, position, phone);
        getTeacherData().add(teacher);
        Collections.sort(teacherData);
        return teacher;
    }

    public void editTeacher(TeacherEntity teacher, String fio, String academicDegree, String position, String phone) {
        TeacherEntity alterTeacher = dbHelper.alterTeacher(teacher, fio, academicDegree, position, phone);
        teacherData.remove(teacher);
        teacherData.add(alterTeacher);
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

    public TimeTableEntity getTimeTable(int idTime) {
        return getTimeTableData().get(idTime);
    }

    public void editTimeTable(Integer id, String beginTime, String endTime) {
        TimeTableEntity newItem = dbHelper.alterTimeTable(id, beginTime, endTime);
        timeTableData.remove(id);
        timeTableData.put(id, newItem);
    }

    //endregion

    //region USER ENTITY
    public ObservableList<UserEntity> getUserData() {
        return initUserData();
        //return userData == null ? initUserData() : userData;
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
        getUserData().add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public UserEntity addUser(String login, CategoryEntity category) {
        UserEntity newUser = dbHelper.insertUser(login, category);
        getUserData().add(newUser);
        Collections.sort(userData);
        return newUser;
    }

    public void editUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        UserEntity alterUser = dbHelper.alterUser(user, newLogin, newCategory);
        getUserData().remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void editUser(UserEntity user, String pass) {
        String salt = SaltGenerator.generate();
        UserEntity alterUser = dbHelper.alterUser(user, HashText.getHash(pass, salt), salt);
        getUserData().remove(user);
        userData.add(alterUser);
        Collections.sort(userData);
    }

    public void removeUser(UserEntity user) {
        dbHelper.deleteUser(user);
        getUserData().remove(user);
    }
    //endregion
}