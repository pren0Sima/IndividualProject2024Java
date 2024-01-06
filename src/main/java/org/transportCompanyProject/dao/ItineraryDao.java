package org.transportCompanyProject.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.dto.ItineraryDto;
import org.transportCompanyProject.entity.*;
import org.transportCompanyProject.exceptions.*;
import org.transportCompanyProject.interfaces.Accounting;

import java.math.BigDecimal;
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
            itineraries = session.createQuery("Select i From Itinerary i", Itinerary.class)
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

    public static BigDecimal calculatePriceWithOvercharge(BigDecimal basePrice, BigDecimal overcharge) {
        return basePrice.multiply(overcharge.add(BigDecimal.ONE));
    }

    public static BigDecimal getCompanyOverchargeThroughItinerary(Itinerary itinerary) {
        return CompanyDao.getCompanyById(itinerary.getVehicle().getCompany().getId()).getOvercharge();
    }

    public static boolean validateItinerary(Itinerary itinerary) throws ItineraryLacksVitalInformation {
        if (itinerary == null || itinerary.getCost() == null
                || itinerary.getClient() == null
                || itinerary.getVehicle() == null)
            throw new ItineraryLacksVitalInformation("Itinerary does not exist or lacks cost, client and/or vehicle!");
        else return true;
    }
    public static void executeItinerary(Itinerary itinerary) throws ClientDoesNotExistException,
            ItineraryLacksVitalInformation, VehicleHasNoCompanyException, AmountShouldBePositiveException, NotEnoughMoneyInCompanyException {
        // 1. Make an obligation after validating client and itinerary
        Client client = ClientDao.getClientById(itinerary.getClient().getId());
        Vehicle vehicle = VehicleDao.getVehicleById(itinerary.getVehicle().getId());
        if (ClientDao.validateClient(client)
                && validateItinerary(itinerary)
                && VehicleDao.validateVehicle(vehicle)) {
            Obligation obligation = new Obligation(client, itinerary);
            ObligationDao.saveOrUpdateObligation(obligation);
            // 2. Check if client can pay full amount:
            // 2.1. calculate price
            BigDecimal cost = itinerary.getCost();
            BigDecimal priceToPay = ItineraryDao.calculatePriceWithOvercharge
                    (cost, getCompanyOverchargeThroughItinerary(itinerary));
            // if client can pay -> they pay and their obligation is paid.
            boolean clientPays = ClientDao.canAClientPay(priceToPay, client);

            Company company = CompanyDao.getCompanyById(vehicle.getCompany().getId());
            Accounting companyAccounting = null;
            if (clientPays) {
                Accounting clientAccounting = null;
                clientAccounting.subtractFromBalance(priceToPay, client);
                // pay company
                companyAccounting.addToBalance(priceToPay, company);
                obligation.setPaid(true);
                // update base:
                ClientDao.saveOrUpdateClient(client);
                ObligationDao.saveOrUpdateObligation(obligation);
                CompanyDao.saveOrUpdateCompany(company);
            } else {
                if (CompanyDao.canACompanyPay(cost, company)){
                    // company pays:
                    companyAccounting.subtractFromBalance(cost, company);
                    CompanyDao.saveOrUpdateCompany(company);
                }
                else {
                    throw new NotEnoughMoneyInCompanyException("Company cannot afford this itinerary!");
                }
            }
        }
    }
}
