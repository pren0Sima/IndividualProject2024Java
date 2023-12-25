package org.transportCompanyProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters long!")
    @Pattern(regexp = "^([A-Z].*)", message = "First name should start with a capital letter!")
    private String name;

    @PositiveOrZero
    @Column(name="balance")
    private BigDecimal balance;
}
