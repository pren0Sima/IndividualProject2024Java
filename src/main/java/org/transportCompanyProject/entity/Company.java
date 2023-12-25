package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name="transport_company")
public class Company {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @Size(min = 1, max = 30, message = "Company name must be 1-30 characters long!")
    @Pattern(regexp = "^[A-Z].*", message = "Company should start with a capital letter!")
    private String name;

    // collection of employees
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
