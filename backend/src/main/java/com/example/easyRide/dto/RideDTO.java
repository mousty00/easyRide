package com.example.easyRide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideDTO {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long id;
    @NotEmpty
    @NotBlank
    private String state;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Double distance;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long idUser;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Double price;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long idDriver;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long idVehicle;

    public RideDTO(RideDTO rideDTO) {
        this.id = rideDTO.getId();
        this.state = rideDTO.getState();
        this.distance = rideDTO.getDistance();
        this.startDate = rideDTO.getStartDate();
        this.endDate = rideDTO.getEndDate();
        this.idUser = rideDTO.getIdUser();
        this.price = rideDTO.getPrice();
        this.idDriver = rideDTO.getIdDriver();
        this.idVehicle = rideDTO.getIdVehicle();
    }
}
