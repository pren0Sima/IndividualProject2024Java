package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Table(name = "vehicle_type")
@Entity
public class VehicleType {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="type", nullable = false)
    @Size(min = 2, max = 30, message = "Type must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([A-Z].*)", message = "Vehicle type should start with a capital letter!")
    private String type;

    public VehicleType() {
    }

    public VehicleType(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public VehicleType(String type) {
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

    @Override
    public String toString() {
        return "VehicleType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
