package com.example.easyRide.dto.info;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorBodyInfo(
        String message,
        int status,
        Instant timestamp
) {
}
