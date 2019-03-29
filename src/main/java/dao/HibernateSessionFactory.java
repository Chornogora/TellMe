package dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import model.*;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory = null;

    static {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Variant.class);
        config.addAnnotatedClass(Word.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        sessionFactory = config.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
