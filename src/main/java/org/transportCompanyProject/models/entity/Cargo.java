package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cargo")
// creates the base class' table, bc it's an entity
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

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                '}';
    }
}
