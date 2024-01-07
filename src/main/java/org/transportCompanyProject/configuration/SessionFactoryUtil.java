package org.transportCompanyProject.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.transportCompanyProject.models.entity.*;

/**
 * A class for adding annotated as @Entity classes as tables in the database.
 */
public class SessionFactoryUtil {
    /**
     * An instance of the Hibernate SessionFactory.
     */
    private static SessionFactory sessionFactory;

    /**
     * Retrieves the instance of the Hibernate SessionFactory. If the
     * SessionFactory is not yet initialized, it creates a new instance by
     * configuring Hibernate with annotated classes.
     * @return the Hibernate SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Itinerary.class);
            configuration.addAnnotatedClass(Vehicle.class);
            configuration.addAnnotatedClass(VehicleType.class);
            configuration.addAnnotatedClass(Driver.class);
            configuration.addAnnotatedClass(DrivingQualification.class);
            configuration.addAnnotatedClass(Cargo.class);
            configuration.addAnnotatedClass(Passenger.class);
            configuration.addAnnotatedClass(Goods.class);
            configuration.addAnnotatedClass(Obligation.class);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}
