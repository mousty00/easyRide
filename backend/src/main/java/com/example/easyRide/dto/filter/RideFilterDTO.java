package com.example.easyRide.dto.filter;

import java.time.LocalDate;

public record RideFilterDTO(
        Long id,
        String state,
        double distance,
        LocalDate startDate,
        LocalDate endDate,
        Long idUser,
        Double price,
        Long idDriver,
        Long idVehicle
) {
}
