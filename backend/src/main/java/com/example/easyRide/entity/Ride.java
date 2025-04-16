package com.example.easyRide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@Entity
@Table(name = "rides")
@AllArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Double distance;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "id_driver")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "id_vehicle")
    private Vehicle vehicle;

    public Ride() {
    }

}
