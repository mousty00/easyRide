package com.example.easyRide.dto;

import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Data
@Builder
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class RideDetailViewDTO {
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
