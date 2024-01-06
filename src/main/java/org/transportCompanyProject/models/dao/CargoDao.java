package org.transportCompanyProject.models.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class CargoDao {
    public static void addCargo(Cargo cargo) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(cargo);
            transaction.commit();
        }
    }
    public static Cargo getCargoById(long id) {
        Cargo client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(Cargo.class, id);
            transaction.commit();
        }
        return client;
    }
    public static void saveOrUpdateCargo(Cargo cargo) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(cargo);
            transaction.commit();
        }
    }
    public static void deleteCargo(Cargo cargo){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(cargo);
            transaction.commit();
        }
    }
    // make a method that returns a list with the joined tables Passenger and Goods
    public static List<Cargo> getFullCargoList(){
        List<Cargo> cargoList = new ArrayList<>();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Passenger> passengerList = session.createQuery("Select p From Passenger p", Passenger.class)
                    .getResultList();
            List<Goods> goodsList = session.createQuery("Select g From Goods g", Goods.class)
                    .getResultList();
            cargoList.addAll(passengerList);
            cargoList.addAll(goodsList);
            transaction.commit();
        }
        return cargoList;
    }
    // criteria query
    // TODO: TEST
    public static Set<Cargo> getItinerarysCargoList(long id){
        Itinerary itinerary;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itinerary = session.createQuery(
                            "select i from Itinerary i" +
                                    " join fetch i.cargo" +
                                    " where c.id = :id",
                            Itinerary.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();
        }
        return itinerary.getCargo();
    }
}
