package orm;

import entity.AcademicPlanEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 21.04.2016.
 */
public class HibernateGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    public static enum DBMS {
        MYSQL,
        MSSQL,
        MARIADB;

        public String getConnectionString() {
            switch (this) {
                case MYSQL: return "hibernate/hibernate.mysql.cfg.xml";
                case MSSQL: return "hibernate/hibernate.mssql.cfg.xml";
                case MARIADB: return "hibernate/hibernate.maria.cfg.xml";
            }
            return null;
        }
    }

    private Class<T> type;
    private static SessionFactory sessionFactory;

    public HibernateGenericDao(Class<T> type) {
        this.type = type;
    }

    public static void buildSessionFactory(DBMS dbms) {
        try {
            sessionFactory = new Configuration().configure(dbms.getConnectionString()).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public PK create(T newInstance) {
        return (PK) getSession().save(newInstance);
    }

    @Override
    public T read(PK id) {
        return getSession().get(type, id);
    }

    @Override
    public void update(T o) {
        getSession().update(o);
    }

    @Override
    public void delete(T o) {
        getSession().delete(o);
    }

    @Override
    public List<T> readCollection() {
        return getSession().createCriteria(type).list();
    }


    public static Collection getCollection(Class<AcademicPlanEntity> aClass) {
        try (Session session = HibernateGenericDao.getSessionFactory().openSession()) {
            return session.createCriteria(aClass).list();
        }
    }


    //other
    public static Collection readCollection(Class cl) {
        return getSession().createCriteria(cl).list();
    }
}
