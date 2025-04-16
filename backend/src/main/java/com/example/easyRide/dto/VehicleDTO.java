package com.example.easyRide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long id;
    @NotEmpty
    @NotBlank
    private String plateNumber;
    @NotEmpty
    @NotBlank
    private String model;
    @NotEmpty
    @NotBlank
    private String color;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Long idDriver;

    public VehicleDTO(VehicleDTO entity) {
        this.id = entity.getId();
        this.plateNumber = entity.getPlateNumber();
        this.model = entity.getModel();
        this.color = entity.getColor();
        this.idDriver = entity.getIdDriver();
    }
}
