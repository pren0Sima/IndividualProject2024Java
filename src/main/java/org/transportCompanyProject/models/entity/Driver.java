package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([A-Z].*)\s([a-zA-Z].*)", message = "First name should start with a capital letter and there should be at least a first and last name!")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DrivingQualification> drivingQualifications;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    private Set<Itinerary> itineraries;

    public Driver() {
    }

    public Driver(String name) {
        this.name = name;
    }

    public Driver(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Driver(long id, String name, Set<DrivingQualification> drivingQualifications) {
        this.id = id;
        this.name = name;
        this.drivingQualifications = drivingQualifications;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DrivingQualification> getDrivingQualifications() {
        return drivingQualifications;
    }

    public void setDrivingQualifications(Set<DrivingQualification> drivingQualifications) {
        this.drivingQualifications = drivingQualifications;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", drivingQualification=" + drivingQualification +
                '}';
    }
}
