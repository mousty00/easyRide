package com.example.easyRide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Getter
@Entity
@Immutable
@Table(name = "ride_detail_view")
public class RideDetailView {
    @Id
    @Column(name = "id_ride")
    private Long Id;
    private String state;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String user;
    private LocalDate birthDate;
    private String driver;
    private String vehicle;
    private String plateNumber;
}
