package com.example.easyRide.dto.filter;

import java.time.LocalDate;
import java.util.List;

public record UserFilterDto(
        Long Id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String taxIdCode,
        LocalDate registrationDate,
        List<Long> rides
) {
}
