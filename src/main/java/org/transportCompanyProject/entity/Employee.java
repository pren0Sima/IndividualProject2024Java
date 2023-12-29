package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Table(name = "employee")
@Entity
public class Employee {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long!")
    @Pattern(regexp = "^([A-Z].*)\s([a-zA-Z].*)", message = "First name should start with a capital letter and there should be at least a first and last name!")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name="position")
    private PositionType positionType;

    @Positive
    @Digits(integer = 4, fraction = 2, message = "Salaries should start from 1000.00 and have 2 digits after the decimal point!")
    @Column(name="salary")
    private BigDecimal salary;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Employee(long id, String name, PositionType positionType, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.positionType = positionType;
        this.salary = salary;
    }

    public Employee(long id, String name, PositionType positionType) {
        this.id = id;
        this.name = name;
        this.positionType = positionType;
    }

    public Employee(String name, PositionType positionType) {
        this.name = name;
        this.positionType = positionType;
    }

    public Employee() {
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

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", positionType=" + positionType +
                ", salary=" + salary +
//                ", company=" + company +
                '}';
    }
}
