package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="itinerary")
public class Itinerary {
    @Column(name="id", nullable=false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name="starting_point")
    @Size(min = 7, max = 100, message = "Starting point must be between 7 and 100 characters long!")
    private String startingPoint;

    @Column(name="destination")
    // add a criteria that destination != starting point
    @Size(min = 7, max = 100, message = "Destination must be between 7 and 100 characters long!")
    private String destination;

    @Column(name="date_of_departure")
    // shouldn't be before the date of arrival
    private LocalDate dateOfDeparture;

    @Column(name="date_of_arrival")
    private LocalDate dateOfArrival;

    // cost without company's overcharge?
    @Column(name = "cost")
    @Positive
    private BigDecimal cost;

    // adding foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Cargo> cargo;

    @OneToOne(mappedBy = "itinerary", fetch = FetchType.LAZY)
    private Obligation obligation;
    public Itinerary() {
    }

    public Itinerary(String startingPoint, String destination, LocalDate dateOfDeparture, LocalDate dateOfArrive) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrive;
    }

    public Itinerary(long id, String startingPoint, String destination, LocalDate dateOfDeparture, LocalDate dateOfArrival) {
        this.id = id;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
    }

    public Itinerary(long id, String startingPoint, String destination, LocalDate dateOfDeparture, LocalDate dateOfArrival, BigDecimal cost) {
        this.id = id;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.cost = cost;
    }

    @AssertTrue(message = "Departure date should be before the arrival date!")
    private boolean isDepartureBeforeArrival() {
        return dateOfDeparture == null || dateOfArrival == null || !dateOfDeparture.isAfter(dateOfArrival);
    }

    @AssertTrue(message = "Destination must be different from the starting point!")
    private boolean isDestinationDifferentFromStartingPoint() {
        return startingPoint == null || destination == null || !startingPoint.equals(destination);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<Cargo> getCargo() {
        return cargo;
    }

    public void setCargo(Set<Cargo> cargo) {
        this.cargo = cargo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "id=" + id +
                ", startingPoint='" + startingPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfDeparture=" + dateOfDeparture +
                ", dateOfArrival=" + dateOfArrival +
                ", cost=" + cost +
                '}';
    }
}
