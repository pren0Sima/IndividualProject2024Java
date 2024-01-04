package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

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

    @Positive
    @DecimalMin(value = "0.00")
    private BigDecimal overcharge;

    // collection of employees
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles;

    @PositiveOrZero
    private BigDecimal income = BigDecimal.ZERO;
    @PositiveOrZero
    private BigDecimal expenses = BigDecimal.ZERO;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(long id, String name, BigDecimal overcharge) {
        this.id = id;
        this.name = name;
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

    public BigDecimal getOvercharge() {
        return overcharge;
    }

    public void setOvercharge(BigDecimal overcharge) {
        this.overcharge = overcharge;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
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
