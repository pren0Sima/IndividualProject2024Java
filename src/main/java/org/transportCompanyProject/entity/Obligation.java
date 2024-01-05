package org.transportCompanyProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name="obligation")
public class Obligation {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    private Itinerary itinerary;

    @Column(name = "paid", nullable = false)
    private boolean paid = false;

    public Obligation() {
    }
}
