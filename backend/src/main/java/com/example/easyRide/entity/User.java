package com.example.easyRide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, unique = true, updatable = false)
    private String taxIdCode;
    @Column(nullable = false)
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Ride> rides;

    public User(Long id) {this.Id = id;}
}

