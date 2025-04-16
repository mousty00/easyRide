package com.example.easyRide.dto;

import com.example.easyRide.validation.taxId.TaxId;
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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long Id;
    @NotEmpty
    @NotBlank(message = "insert a valid name")
    private String firstName;
    @NotBlank(message = "insert a valid last name")
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate birthDate;
    @TaxId
    @NotEmpty
    @NotBlank
    private String taxIdCode;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate registrationDate;
    @NotEmpty(message = "Invalid rides")
    private List<Long> rides;

    public UserDTO(UserDTO userDTO) {
        if(userDTO != null){
            this.Id = userDTO.Id;
            this.firstName = userDTO.getFirstName();
            this.lastName = userDTO.getLastName();
            this.birthDate = userDTO.getBirthDate();
            this.taxIdCode = userDTO.getTaxIdCode();
            this.registrationDate = userDTO.getRegistrationDate();
            this.rides = userDTO.getRides();
        }
    }
}
