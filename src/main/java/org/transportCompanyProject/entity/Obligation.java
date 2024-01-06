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

    public Obligation(long id, Client client, Itinerary itinerary) {
        this.id = id;
        this.client = client;
        this.itinerary = itinerary;
    }

    public Obligation(Client client, Itinerary itinerary) {
        this.client = client;
        this.itinerary = itinerary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Obligation{" +
                "id=" + id +
//                ", client=" + client +
//                ", itinerary=" + itinerary +
                ", paid=" + paid +
                '}';
    }
}
