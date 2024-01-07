package org.transportCompanyProject.models.dto;

import org.transportCompanyProject.models.entity.Client;
import org.transportCompanyProject.models.entity.Driver;
import org.transportCompanyProject.models.entity.Vehicle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Data Transfer Object (DTO) class representing an Itinerary for use in data exchange between layers.
 */
public class ItineraryDto implements Serializable {
    private long id;

    private String startingPoint;

    private String destination;

    private LocalDate dateOfDeparture;

    private LocalDate dateOfArrival;

    private BigDecimal cost;
    // may use a dto?
    private Vehicle vehicle;

    // may change it to dto?
    private Driver driver;

    private Client client;

    public ItineraryDto(long id, String startingPoint, String destination,
                        LocalDate dateOfDeparture, LocalDate dateOfArrival, BigDecimal cost,
                        Vehicle vehicle, Driver driver, Client client) {
        this.id = id;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.cost = cost;
        this.vehicle = vehicle;
        this.driver = driver;
        this.client = client;
    }
    /**
     * An overridden toString() method that displays a ItineraryDto's id, startingPoint, destination,
     * dateOfDeparture, dateOfArrival, cost, vehicle, driver name and client name.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "ItineraryDto{" +
                "id=" + id +
                ", startingPoint='" + startingPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfDeparture=" + dateOfDeparture +
                ", dateOfArrival=" + dateOfArrival +
                ", cost=" + cost +
                ", vehicle=" + vehicle +
                ", driver=" + driver.getName() +
                ", client=" + client.getName() +
                '}';
    }
}
