package org.transportCompanyProject.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.ItineraryDto;
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
    // DTO
    public static List<ItineraryDto> getItinerariesDTO() {
        List<ItineraryDto> itineraryDtos;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itineraryDtos = session
                    .createQuery("select new org.transportCompanyProject.dto.ItineraryDto(i.id, i.startingPoint, i.destination," +
                            " i.dateOfDeparture, i.dateOfArrival, i.cost, " +
                            " i.vehicle, i.driver, i.client) " +
                            "from Itinerary i", ItineraryDto.class)
                    .getResultList();
            transaction.commit();
        }
        return itineraryDtos;
    }

    // criteria queries:
    // get by same destination
    public static List<Itinerary> itinerariesFindByDestination(String destinationSubstring) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Itinerary> cr = cb.createQuery(Itinerary.class);
            Root<Itinerary> root = cr.from(Itinerary.class);
            cr.select(root).where(cb.like(root.get("destination"), "%" + destinationSubstring + "%"));

            Query<Itinerary> query = session.createQuery(cr);
            List<Itinerary> itineraries = query.getResultList();
            return itineraries;
        }
    }
    // sort lexicographically
    public static List<Itinerary> getOrderedItinerariesByDestination() {
        List<Itinerary> itineraries;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itineraries = session.createQuery("Select i From Itinerary i" +
                            " ORDER BY i.destination", Itinerary.class)
                    .getResultList();
            transaction.commit();
        }
        return itineraries;
    }
}
