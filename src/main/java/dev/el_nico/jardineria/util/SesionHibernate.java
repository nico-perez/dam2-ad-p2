package dev.el_nico.jardineria.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SesionHibernate {
    
    private static final Session INSTANCE = setUpSession();

    private SesionHibernate() {}

    private static final synchronized Session setUpSession() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            return sessionFactory.openSession();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
            return null;
        }
    }

    public static final Session get() {
        return INSTANCE;
    }

}
