package com.example.easyRide.dto.auth;

import com.example.easyRide.dto.UserDTO;

public record UserLoginInfoDTO(
        String message,
        String token,
        UserDTO user
) {
}
