package com.example.easyRide.integration.testCases;

import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.dto.VehicleDTO;

import java.time.LocalDate;
import java.util.List;

class TestVariables {
    private static final RideDTO firstRide = RideDTO.builder()
            .id(1L)
            .state("completed")
            .distance(356.45)
            .startDate(LocalDate.parse("2023-01-15"))
            .endDate(LocalDate.parse("2023-01-16"))
            .idUser(1L)
            .price(45.75)
            .idDriver(1L)
            .idVehicle(1L)
            .build();

    private static final RideDTO secondRide = RideDTO.builder()
            .id(2L)
            .state("cancelled")
            .distance(520.32)
            .startDate(LocalDate.parse("2023-02-10"))
            .endDate(LocalDate.parse("2023-02-11"))
            .idUser(2L)
            .price(30.20)
            .idDriver(2L)
            .idVehicle(2L)
            .build();

    private static final VehicleDTO firstVehicle = VehicleDTO.builder()
            .id(1L)
            .plateNumber("AB901LM")
            .model("Tesla CyberTruck")
            .color("Silver")
            .idDriver(1L)
            .build();

    private static final VehicleDTO secondVehicle = VehicleDTO.builder()
            .id(2L)
            .plateNumber("XY432JK")
            .model("Volkswagen Tiguan")
            .color("Yellow")
            .idDriver(2L)
            .build();

    protected final static List<DriverDTO> EXPECTED_DRIVERS = List.of(
            DriverDTO.builder()
                    .id(1L)
                    .firstName("Alessandro")
                    .lastName("Conti")
                    .birthDate(LocalDate.parse("1980-01-15"))
                    .taxIdCode("CNTALS80A15A0")
                    .vehicles(List.of(firstVehicle.getId()))
                    .rides(List.of(firstRide.getId()))
                    .build(),
            DriverDTO.builder()
                    .id(2L)
                    .firstName("Carla")
                    .lastName("Marini")
                    .birthDate(LocalDate.parse("1987-03-22"))
                    .taxIdCode("MRNCRL87C62A1")
                    .vehicles(List.of(secondVehicle.getId()))
                    .rides(List.of(secondRide.getId()))
                    .build()
    );

    protected final static List<RideDTO> EXPECTED_RIDES = List.of(
            firstRide,
            secondRide
    );

    protected final static List<UserDTO> EXPECTED_USERS = List.of(
            UserDTO.builder()
                    .Id(1L)
                    .firstName("Moustapha")
                    .lastName("Ndiaye")
                    .birthDate(LocalDate.parse("1985-06-15"))
                    .taxIdCode("RSSMRC85H15A0")
                    .registrationDate(LocalDate.parse("2023-01-15"))
                    .rides(List.of(firstRide.getId()))
                    .build(),
            UserDTO.builder()
                    .Id(2L)
                    .firstName("Luca")
                    .lastName("Bianchi")
                    .birthDate(LocalDate.parse("1990-04-20"))
                    .taxIdCode("BNCLCU90D20A1")
                    .registrationDate(LocalDate.parse("2023-02-10"))
                    .rides(List.of(EXPECTED_RIDES.get(1).getId()))
                    .build()
    );

    protected final static List<VehicleDTO> EXPECTED_VEHICLES = List.of(
            firstVehicle,
            secondVehicle
    );
}
