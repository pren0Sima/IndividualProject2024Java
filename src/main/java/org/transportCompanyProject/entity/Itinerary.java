package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

@Entity
@Table(name="itinerary")
public class Itinerary {
    @Column(name="id", nullable=false)
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

    @AssertTrue(message = "Departure date should be before the arrival date")
    private boolean isDepartureBeforeArrival() {
        return dateOfDeparture == null || dateOfArrival == null || !dateOfDeparture.isAfter(dateOfArrival);
    }

    @AssertTrue(message = "Destination must be different from the starting point")
    private boolean isDestinationDifferentFromStartingPoint() {
        return startingPoint == null || destination == null || !startingPoint.equals(destination);
    }
    @Override
    public String toString() {
        return "Itinerary{" +
                "id=" + id +
                ", startingPoint='" + startingPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfDeparture=" + dateOfDeparture +
                ", dateOfArrive=" + dateOfArrival +
                '}';
    }
}
