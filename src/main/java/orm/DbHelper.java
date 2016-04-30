package orm;

import entity.*;
import org.hibernate.Session;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by User on 21.04.2016.
 */
public enum DbHelper {
    INSTANCE;

    //region ACADEMIC PLAN ENTITY
    //endregion

    //region AUDIENCE ENTITY
    public Collection getAudienceData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(AudienceEntity.class).list();
        }
    }

    public AudienceEntity insertAudience(String id, AudienceTypeEntity type, int capacity) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            AudienceEntity audience = new AudienceEntity(id, type, capacity);
            session.save(audience);
            session.getTransaction().commit();
            return audience;
        }
    }

    public void alterAudience(AudienceEntity audience, AudienceEntity newAudience) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            audience = session.get(AudienceEntity.class, audience.getId());
            audience.setId(newAudience.getId());
            audience.setAudienceType(newAudience.getAudienceType());
            audience.setCapacity(newAudience.getCapacity());
            session.getTransaction().commit();
        }
    }

    public void deleteAudience(AudienceEntity audience) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(audience);
            session.getTransaction().commit();
        }
    }
    //endregion

    //region AUDIENCE TYPE ENTITY
    public Collection getAudienceTypeData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(AudienceTypeEntity.class).list();
        }
    }
    //endregion

    //region CATEGORY ENTITY
    public Collection getCategoryData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(CategoryEntity.class).list();
        }
    }
    //endregion

    //region DIRECTION ENTITY
    public Collection getDirectionData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(DirectionEntity.class).list();
        }
    }
    //endregion

    //region DISCIPLINE ENTITY
    //endregion

    //region DISCIPLINE TYPE ENTITY
    //endregion

    //region GROUP ENTITY
    public Collection getGroupData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(GroupEntity.class).list();
        }
    }
    //endregion

    //region MENTOR ENTITY
    //endregion

    //region NAVIGATOR ENTITY
    //endregion

    //region SCHEDULE ITEM ENTITY
    //endregion

    //region TEACHER ENTITY
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

    public void alterTeacher(TeacherEntity teacher, TeacherEntity newTeacher) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            session.beginTransaction();
            teacher = session.get(TeacherEntity.class, teacher.getId());
            teacher.setFio(newTeacher.getFio());
            teacher.setAcademicDegree(newTeacher.getAcademicDegree());
            teacher.setPosition(newTeacher.getPosition());
            teacher.setPhone(newTeacher.getPhone());
            session.getTransaction().commit();
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

    //region TIME TABLE ENTITY
    public Collection getTimeTableData() {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(TimeTableEntity.class).list();
        }
    }
    //endregion

    //region USER ENTITY
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
            UserEntity user = new UserEntity();
            user.setLogin(login);
            user.setCategory(category);
            user.setDateCreation(new Date(Calendar.getInstance().getTime().getTime()));
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