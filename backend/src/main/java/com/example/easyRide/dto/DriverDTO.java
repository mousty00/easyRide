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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long id;
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate birthDate;
    @NotEmpty
    @NotBlank
    @TaxId
    private String taxIdCode;
    private List<Long> rides;
    private List<Long> vehicles;

    public DriverDTO(DriverDTO driver) {
        this.id = driver.getId();
        this.firstName = driver.getFirstName();
        this.lastName = driver.getLastName();
        this.birthDate = driver.getBirthDate();
        this.taxIdCode = driver.getTaxIdCode();
        this.rides = driver.getRides();
        this.vehicles = driver.getRides();
    }
}
