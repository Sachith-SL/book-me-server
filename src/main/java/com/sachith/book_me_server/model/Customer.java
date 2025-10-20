package com.sachith.book_me_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

