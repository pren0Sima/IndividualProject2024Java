package org.transportCompanyProject.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

//    @Column(name = "vehicle_type")
    @ManyToOne(fetch = FetchType.LAZY)
    private VehicleType vehicleType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private Set<Itinerary> itineraries;
    public Vehicle() {
    }

    public Vehicle(long id, Company company, VehicleType vehicleType) {
        this.id = id;
        this.company = company;
        this.vehicleType = vehicleType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                '}';
    }
}
