package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * An entity class DrivingQualification. It has an id and type.
 * It is referenced in entity class Driver.
 */
@Entity
@Table(name = "driving_qualifications")
public class DrivingQualification {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @Column(name="type", nullable = false, unique = true)
    private String type;
    @ManyToMany(mappedBy = "drivingQualifications")
    private Set<Driver> drivers;
    public DrivingQualification() {
    }

    public DrivingQualification(String type) {
        this.type = type;
    }

    public DrivingQualification(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    /**
     * An overridden toString() method, displaying a DriverQualification object's id and type.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "DrivingQualifications{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
