package com.example.easyRide.integration.testCases;

import com.example.easyRide.dto.VehicleDTO;
import com.example.easyRide.integration.GenericApiTest;

import java.util.List;

import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_DRIVERS;
import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_VEHICLES;

public class VehicleIntegrationTest extends GenericApiTest<VehicleDTO, Long> {
    @Override
    protected String getBasePath() {
        return "/api/vehicles";
    }

    @Override
    protected List<VehicleDTO> getExpectedEntities() {
        return EXPECTED_VEHICLES;
    }

    @Override
    protected VehicleDTO createNewEntity() {
        return VehicleDTO.builder()
                .color("black")
                .plateNumber("MT0392")
                .model("Ford")
                .idDriver(EXPECTED_DRIVERS.getLast().getId())
                .build();
    }

    @Override
    protected VehicleDTO modifyEntity(VehicleDTO entity) {
        VehicleDTO modifiedVehicle = new VehicleDTO(entity);
        modifiedVehicle.setColor("Green");
        return modifiedVehicle;
    }

    @Override
    protected Long getEntityId(VehicleDTO entity) {
        return entity.getId();
    }

    @Override
    protected Class<VehicleDTO> getEntityClass() {
        return VehicleDTO.class;
    }
}
