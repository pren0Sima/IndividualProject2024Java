package org.transportCompanyProject.entity;

import jakarta.persistence.*;
// doesn't create the base class' table, since it's not an entity
@MappedSuperclass
//@Entity
@Table(name = "cargo")
// creates the base class' table, bc it's an entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class Cargo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
