package org.transportCompanyProject.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
/**
 * An entity class Passenger. It has an id and name fields.
 */
@Entity
@Table(name = "passenger")
public class Passenger extends Cargo {
    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([A-Z].*)\s([a-zA-Z].*)", message = "First name should start with a capital letter and there should be at least a first and last name!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passenger() {
    }

    public Passenger(String name) {
        this.name = name;
    }

    public Passenger(long id, String name) {
        super(id);
        this.name = name;
    }
    /**
     * An overridden toString() method that displays a Passenger object's id and name.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
