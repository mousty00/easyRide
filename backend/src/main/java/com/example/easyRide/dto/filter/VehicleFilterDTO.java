package com.example.easyRide.dto.filter;

public record VehicleFilterDTO(
        Long id,
        String plateNumber,
        String model,
        String color,
        Long idDriver
) {
}
