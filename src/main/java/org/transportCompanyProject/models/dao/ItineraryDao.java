package org.transportCompanyProject.models.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.transportCompanyProject.models.entity.*;
import org.transportCompanyProject.configuration.SessionFactoryUtil;
import org.transportCompanyProject.models.dto.ItineraryDto;
import org.transportCompanyProject.exceptions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Data Access Object (DAO) class for performing database operations related to Itinerary entities.
 */
public class ItineraryDao {
    /**
     * Adds a new Itinerary to the database.
     *
     * @param itinerary The Itinerary object to be added.
     */
    public static void addItinerary(Itinerary itinerary) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be save(), but it's deprecated
            session.persist(itinerary);
            transaction.commit();
        }
    }
    /**
     * Deletes an Itinerary from the database.
     *
     * @param itinerary The Itinerary object to be deleted.
     */
    public static void deleteItinerary(Itinerary itinerary){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            // it used to be delete(), but it's deprecated
            session.remove(itinerary);
            transaction.commit();
        }
    }
    /**
     * Retrieves an Itinerary by its id from the database.
     *
     * @param id The id of the Itinerary to be retrieved.
     * @return The Itinerary object with the specified id, or null if not found.
     */
    public static Itinerary getItineraryById(long id) {
        Itinerary itinerary;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itinerary = session.get(Itinerary.class, id);
            transaction.commit();
        }
        return itinerary;
    }
    /**
     * Saves or updates an existing Itinerary in the database.
     *
     * @param itinerary The Itinerary object to be saved or updated.
     */
    public static void saveOrUpdateItinerary(Itinerary itinerary) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // it used to be saveOrUpdate(), but it's deprecated
            session.merge(itinerary);
            transaction.commit();
        }
    }
    /**
     * Retrieves a list of all Itineraries from the database.
     *
     * @return List of all Itineraries in the database.
     */
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
    /**
     * Retrieves a list of ItineraryDto objects, representing Itineraries, from the database.
     *
     * @return List of ItineraryDto objects.
     */
    public static List<ItineraryDto> getItinerariesDTO() {
        List<ItineraryDto> itineraryDtos;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            itineraryDtos = session
                    .createQuery("select new org.transportCompanyProject.models.dto.ItineraryDto(i.id, i.startingPoint, i.destination," +
                            " i.dateOfDeparture, i.dateOfArrival, i.cost, " +
                            " i.vehicle, i.driver, i.client) " +
                            "from Itinerary i", ItineraryDto.class)
                    .getResultList();
            transaction.commit();
        }
        return itineraryDtos;
    }
    /**
     * Retrieves a list of Itineraries with a destination containing the specified substring.
     *
     * @param destinationSubstring The substring to search for in the destination.
     * @return List of matching Itineraries.
     */
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
    /**
     * Retrieves a list of Itineraries sorted lexicographically by destination.
     *
     * @return List of Itineraries sorted by destination.
     */
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
    /**
     * Adds a Cargo to an Itinerary, updating the database accordingly.
     *
     * @param cargo     The Cargo object to be added.
     * @param itinerary The Itinerary object to which the Cargo is added.
     */
    public static void addCargoToItinerary(Cargo cargo, Itinerary itinerary) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (itinerary == null) {
                itinerary = new Itinerary();
            }
            if (itinerary.getCargo() == null){
                Set<Cargo> cargos = new HashSet<>();
                itinerary.setCargo(cargos);
            }
            itinerary.getCargo().add(cargo);
            // if the cargo is not in the database => add it; same for the driver
            CargoDao.saveOrUpdateCargo(cargo);
            ItineraryDao.saveOrUpdateItinerary(itinerary);

            transaction.commit();
        }
    }
    /**
     * Calculates the price with overcharge based on the given base price and overcharge percentage.
     *
     * @param basePrice  The base price of the Itinerary.
     * @param overcharge The overcharge percentage.
     * @return The calculated price with overcharge.
     */
    public static BigDecimal calculatePriceWithOvercharge(BigDecimal basePrice, BigDecimal overcharge) {
        return basePrice.multiply(overcharge.add(BigDecimal.ONE));
    }
    /**
     * Gets the overcharge percentage of the company associated with the given Itinerary.
     *
     * @param itinerary The Itinerary from which to retrieve the overcharge.
     * @return The overcharge percentage of the associated company.
     */
    public static BigDecimal getCompanyOverchargeThroughItinerary(Itinerary itinerary) {
        return CompanyDao.getCompanyById(itinerary.getVehicle().getCompany().getId()).getOvercharge();
    }
    /**
     * Validates whether the given Itinerary contains vital information.
     *
     * @param itinerary The Itinerary to be validated.
     * @return True if the Itinerary is valid, false otherwise.
     * @throws ItineraryLacksVitalInformation If the Itinerary lacks vital information.
     */
    public static boolean validateItinerary(Itinerary itinerary) throws ItineraryLacksVitalInformation {
        if (itinerary == null || itinerary.getCost() == null
                || itinerary.getClient() == null
                || itinerary.getVehicle() == null)
            throw new ItineraryLacksVitalInformation("Itinerary does not exist or lacks cost, client and/or vehicle!");
        else return true;
    }
    /**
     * Executes an Itinerary, performing necessary obligations and transactions.
     *
     * @param itinerary The Itinerary to be executed.
     * @throws ClientDoesNotExistException      If the client associated with the Itinerary does not exist.
     * @throws ItineraryLacksVitalInformation   If the Itinerary lacks vital information.
     * @throws VehicleHasNoCompanyException     If the associated Vehicle has no associated Company.
     * @throws AmountShouldBePositiveException  If the amount to be paid is not positive.
     * @throws NotEnoughMoneyInCompanyException If the Company does not have enough money to cover the costs.
     */
    public static void executeItinerary(Itinerary itinerary) throws ClientDoesNotExistException,
            ItineraryLacksVitalInformation, VehicleHasNoCompanyException, AmountShouldBePositiveException,
            NotEnoughMoneyInCompanyException {
        // 1. Make an obligation after validating client and itinerary
        Client client = ClientDao.getClientById(itinerary.getClient().getId());
        Vehicle vehicle = VehicleDao.getVehicleById(itinerary.getVehicle().getId());
        if (ClientDao.validateClient(client)
                && validateItinerary(itinerary)
                && VehicleDao.validateVehicle(vehicle)) {
            Obligation obligation = new Obligation(itinerary.getId(), client, itinerary);
            ObligationDao.saveOrUpdateObligation(obligation);
            // 2. Check if client can pay full amount:
            // 2.1. calculate price
            BigDecimal cost = itinerary.getCost();
            BigDecimal priceToPay = ItineraryDao.calculatePriceWithOvercharge
                    (cost, getCompanyOverchargeThroughItinerary(itinerary));
            // if client can pay -> they pay and their obligation is paid.
            boolean clientPays = ClientDao.canAClientPay(priceToPay, client);

            Company company = CompanyDao.getCompanyById(vehicle.getCompany().getId());
            if (clientPays) {
                ClientDao.subtractFromBalance(priceToPay, client);
                // pay company
                CompanyDao.addToBalance(priceToPay, company);
                obligation.setPaid(true);
                // update base:
                ObligationDao.saveOrUpdateObligation(obligation);
            } else {
                if (CompanyDao.canACompanyPay(cost, company)){
                    // company pays:
                    CompanyDao.subtractFromBalance(cost, company);
                }
                else {
                    throw new NotEnoughMoneyInCompanyException("Company cannot afford this itinerary!");
                }
            }
        }
    }

    /**
     * Counts the number of itineraries executed before the current date. It uses Dtos.
     *
     * @return the count of the executed itineraries
     */
    public static int countExecutedItinerariesDTO() {
        int count = 0;
        List<ItineraryDto> itineraryDtoList = getItinerariesDTO();
        for(ItineraryDto itineraryDto : itineraryDtoList) {
            if(itineraryDto.getDateOfArrival().isBefore(LocalDate.now())){
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total cost of the executed itineraries in the database.
     *
     * @return a BigDecimal value of the sum of the costs.
     */
    public static BigDecimal totalCostOfExecutedItineraries() {
        LocalDate currentDate = LocalDate.now();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Object result = session.createQuery("SELECT SUM(cost) FROM Itinerary" +
                            " WHERE dateOfArrival < :currentDate", Itinerary.class)
                    .setParameter("currentDate", currentDate)
                    .getSingleResult();
            if (result != null) {
                return (BigDecimal) result;
            }
            transaction.commit();
        }
        return BigDecimal.ZERO;
    }
}
