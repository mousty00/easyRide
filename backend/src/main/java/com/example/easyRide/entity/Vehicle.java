package com.example.easyRide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "vehicles")
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false, unique = true)
    private String plateNumber;
    @Column(nullable = false)
    private String color;
    @ManyToOne
    @JoinColumn(name = "id_driver")
    private Driver driver;

    public Vehicle() {
    }

    public Vehicle(Long id) {
        this.id = id;
    }

}
