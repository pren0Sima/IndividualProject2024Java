package org.transportCompanyProject.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.Employee;
import org.transportCompanyProject.entity.Itinerary;

import java.util.List;

public class ItineraryDao {
    public static void addItinerary(Itinerary itinerary) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(itinerary);
            transaction.commit();
        }
    }

    public static void deleteItinerary(Itinerary itinerary){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(itinerary);
            transaction.commit();
        }
    }
    public static Itinerary getItineraryById(long id) {
        Itinerary itinerary;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itinerary = session.get(Itinerary.class, id);
            transaction.commit();
        }
        return itinerary;
    }
    public static void saveOrUpdateItinerary(Itinerary itinerary) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(itinerary);
            transaction.commit();
        }
    }
    public static List<Itinerary> getItineraries() {
        List<Itinerary> itineraries;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itineraries = session.createQuery("Select c From Itinerary c", Itinerary.class)
                    .getResultList();
            transaction.commit();
        }
        return itineraries;
    }

}
