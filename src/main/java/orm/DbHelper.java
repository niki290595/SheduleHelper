package orm;

import entity.*;
import org.hibernate.Session;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 21.04.2016.
 */
public enum DbHelper {
    INSTANCE;

    //region !ACADEMIC PLAN ENTITY
    public Collection getAcademicPlanData() {
        return HibernateGenericDao.getCollection(AcademicPlanEntity.class);
        /*
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(AcademicPlanEntity.class).list();
        }
        */
    }

    public AcademicPlanEntity addDiscipline(DisciplineEntity discipline, DirectionEntity direction) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            AcademicPlanEntity academicPlan = new AcademicPlanEntity(direction, discipline);
            academicPlan.setId((Integer) session.save(academicPlan));
            session.getTransaction().commit();
            return academicPlan;
        }
    }

    public void deleteDiscipline(DisciplineEntity discipline, DirectionEntity direction) {
        Collection<AcademicPlanEntity> academicPlanList = getAcademicPlanData();
        AcademicPlanEntity academicPlanForDelete = null;
        for (AcademicPlanEntity academicPlan : academicPlanList) {
            if (academicPlan.getDirection().equals(direction) &&
                    academicPlan.getDiscipline().equals(discipline)) {
                academicPlanForDelete = academicPlan;
                break;
            }
        }
        if (academicPlanForDelete == null) return;

        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(academicPlanForDelete);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region !AUDIENCE ENTITY
    public Collection getAudienceData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(AudienceEntity.class).list();
        }
    }

    public AudienceEntity insertAudience(String num, AudienceTypeEntity type, int capacity) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            AudienceEntity audience = new AudienceEntity(num, type, capacity);
            audience.setId((Integer) session.save(audience));
            session.save(audience);
            session.getTransaction().commit();
            return audience;
        }
    }

    public AudienceEntity alterAudience(AudienceEntity audience, String num, AudienceTypeEntity type, int capacity) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            audience = session.get(AudienceEntity.class, audience.getId());
            audience.setNum(num);
            audience.setAudienceType(type);
            audience.setCapacity(capacity);
            session.getTransaction().commit();
        }
        return audience;
    }

    public void deleteAudience(AudienceEntity audience) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(audience);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region !AUDIENCE TYPE ENTITY
    public Collection getAudienceTypeData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(AudienceTypeEntity.class).list();
        }
    }
    //endregion

    //region !CATEGORY ENTITY
    public Collection getCategoryData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(CategoryEntity.class).list();
        }
    }
    //endregion

    //region !DIRECTION ENTITY
    public Collection getDirectionData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(DirectionEntity.class).list();
        }
    }

    public DirectionEntity insertDirection(String name) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            DirectionEntity direction = new DirectionEntity(name);
            direction.setId((Integer) session.save(direction));
            session.getTransaction().commit();
            return direction;
        }
    }

    public DirectionEntity alterDirection(DirectionEntity direction, String name) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            direction = session.get(DirectionEntity.class, direction.getId());
            direction.setName(name);
            session.getTransaction().commit();
            return direction;
        }
    }

    public void deleteDirection(DirectionEntity direction) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(direction);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region !DISCIPLINE ENTITY
    public Collection getDisciplineData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(DisciplineEntity.class).list();
        }
    }

    public DisciplineEntity insertDiscipline(String name) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            DisciplineEntity discipline = new DisciplineEntity(name);
            discipline.setId((Integer) session.save(discipline));
            session.getTransaction().commit();
            return discipline;
        }
    }

    public DisciplineEntity alterDiscipline(DisciplineEntity discipline, String newName) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            discipline = session.get(DisciplineEntity.class, discipline.getId());
            discipline.setName(newName);
            return discipline;
        }
    }

    public void deleteDiscipline(DisciplineEntity discipline) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(discipline);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region DISCIPLINE TYPE ENTITY

    public Collection getDisciplineTypeData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(DisciplineTypeEntity.class).list();
        }
    }

    public DisciplineTypeEntity insertDisciplineType(String name) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            DisciplineTypeEntity disciplineType = new DisciplineTypeEntity(name);
            disciplineType.setId((Integer) session.save(disciplineType));
            session.getTransaction().commit();
            return disciplineType;
        }
    }
    //endregion

    //region !GROUP ENTITY
    public Collection getGroupData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(GroupEntity.class).list();
        }
    }

    public GroupEntity insertGroup(DirectionEntity direction, String name, int studentNumber) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            GroupEntity group = new GroupEntity(name, direction, studentNumber);
            group.setId((Integer) session.save(group));
            session.getTransaction().commit();
            return group;
        }
    }

    public GroupEntity alterGroup(GroupEntity group, DirectionEntity direction, String name, int studentNumber) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            group = session.get(GroupEntity.class, group.getId());
            group.setName(name);
            group.setDirection(direction);
            group.setStudentsNumber(studentNumber);
            session.getTransaction().commit();
            return group;
        }
    }

    public void deleteGroup(GroupEntity group) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region MENTOR ENTITY
    //endregion

    //region NAVIGATOR ENTITY
    //endregion

    //region SCHEDULE ITEM ENTITY

    public Collection getScheduleItemData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(ScheduleItemEntity.class).list();
        }
    }
    //endregion

    //region !TEACHER ENTITY
    public Collection getTeacherData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(TeacherEntity.class).list();
        }
    }

    public TeacherEntity addTeacher(String fio, String academicDegree, String position, String phone) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            TeacherEntity teacher = new TeacherEntity(fio, academicDegree, position, phone);
            teacher.setId((Integer) session.save(teacher));
            session.getTransaction().commit();
            return teacher;
        }
    }

    public TeacherEntity alterTeacher(TeacherEntity teacher, String fio, String academicDegree, String position, String phone) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            teacher = session.get(TeacherEntity.class, teacher.getId());
            teacher.setFio(fio);
            teacher.setAcademicDegree(academicDegree);
            teacher.setPosition(position);
            teacher.setPhone(phone);
            session.getTransaction().commit();
            return teacher;
        }
    }

    public void removeTeacher(TeacherEntity teacher) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(teacher);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region !TIME TABLE ENTITY
    public Collection getTimeTableData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(TimeTableEntity.class).list();
        }
    }

    public TimeTableEntity alterTimeTable(Integer id, String beginTime, String endTime) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            TimeTableEntity time = session.get(TimeTableEntity.class, id);
            time.setTimeBegin(Time.valueOf(beginTime + ":00"));
            time.setTimeEnd(Time.valueOf(endTime + ":00"));
            session.getTransaction().commit();
            return time;
        }
    }
    //endregion

    //region !USER ENTITY
    public Collection getUsersData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(UserEntity.class).list();
        }
    }

    public UserEntity insertUser(String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity(login, pass, category, salt, new Date(Calendar.getInstance().getTime().getTime()));
            user.setId((Integer) session.save(user));
            session.getTransaction().commit();
            return user;
        }
    }

    public UserEntity insertUser(String login, CategoryEntity category) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserEntity user = new UserEntity(login, category,
                    new Date(Calendar.getInstance().getTime().getTime()));
            user.setId((Integer) session.save(user));
            session.getTransaction().commit();
            return user;
        }
    }

    public UserEntity alterUser(UserEntity user, String newLogin, CategoryEntity newCategory) {
        return alterUser(user, newLogin, null, newCategory, null);
    }

    public UserEntity alterUser(UserEntity user, String pass, String salt) {
        return alterUser(user, null, pass, null, salt);
    }

    private UserEntity alterUser(UserEntity user, String login, String pass, CategoryEntity category, String salt) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(UserEntity.class, user.getId());
            if (login != null) user.setLogin(login);
            if (pass != null) user.setPass(pass);
            if (category != null) user.setCategory(category);
            if (salt != null) user.setSalt(salt);
            user.setDateModification(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            session.getTransaction().commit();
            return user;
        }
    }

    public void deleteUser(UserEntity user) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }
    //endregion
}