package org.transportCompanyProject.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="transport_company")
public class Company {
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @Size(min = 1, max = 30, message = "Company name must be 1-30 characters long!")
    @Pattern(regexp = "^[A-Z].*", message = "Company should start with a capital letter!")
    private String name;

    // collection of employees
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles;

    @PositiveOrZero
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Positive
    @Column(name = "overcharge")
    @DecimalMin(value = "0.01", message = "Overcharge should be at least 0.01 (1%) !")
    private BigDecimal overcharge;
    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Company(long id, String name, BigDecimal balance, BigDecimal overcharge) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.overcharge = overcharge;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOvercharge() {
        return overcharge;
    }

    public void setOvercharge(BigDecimal overcharge) {
        this.overcharge = overcharge;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
