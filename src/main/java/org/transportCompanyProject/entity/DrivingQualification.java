package org.transportCompanyProject.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "driving_qualifications")
public class DrivingQualification {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @Column(name="type", nullable = false)

    private String type;

    public DrivingQualification() {
    }

    public DrivingQualification(String type) {
        this.type = type;
    }

    public DrivingQualification(long id, String type) {
        this.id = id;
        this.type = type;
    }
    @ManyToMany(mappedBy = "drivingQualifications")
    private Set<Driver> drivers;

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

    @Override
    public String toString() {
        return "DrivingQualifications{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
