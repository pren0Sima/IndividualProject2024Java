package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;

import java.util.Set;

/**
 * A Cargo entity class. It is inherited by Passenger and Goods. It has an id.
 * It is referenced in entity class Itinerary.
 */
@Entity
@Table(name = "cargo")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cargo {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private Set<Itinerary> itineraries;
    public Cargo() {
    }

    public Cargo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * A toString() overridden method that displays the Cargo's id.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                '}';
    }
}
