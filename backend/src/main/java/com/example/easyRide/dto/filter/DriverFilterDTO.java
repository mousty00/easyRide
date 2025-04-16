package com.example.easyRide.dto.filter;

import java.util.List;

public record DriverFilterDTO(
        Long id,
        String firstName,
        String lastName,
        String taxIdCode,
        List<Long> rides,
        List<Long> vehicles
) {

}
