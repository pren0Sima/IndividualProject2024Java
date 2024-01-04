package org.transportCompanyProject.entity;

import jakarta.persistence.*;

import java.util.Set;

// doesn't create the base class' table, since it's not an entity (MAYBE THAT'S NOT THE WAY TO GO!)
//@MappedSuperclass
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
