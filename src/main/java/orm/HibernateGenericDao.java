package orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 21.04.2016.
 */
public class HibernateGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;
    private static final SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory(registry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public HibernateGenericDao(Class<T> type) {
        this.type = type;
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


}
