package com.example.easyRide.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {
    @NotBlank(message = "insert a valid name")
    private String firstName;
    @NotBlank(message = "insert a valid last name")
    private String lastName;
}