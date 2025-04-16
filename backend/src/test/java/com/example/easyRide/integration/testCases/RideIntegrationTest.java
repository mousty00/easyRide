package com.example.easyRide.integration.testCases;

import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.integration.GenericApiTest;

import java.time.LocalDate;
import java.util.List;

import static com.example.easyRide.integration.testCases.TestVariables.*;

public class RideIntegrationTest extends GenericApiTest<RideDTO, Long> {

    @Override
    protected String getBasePath() {
        return "/api/rides";
    }

    @Override
    protected List<RideDTO> getExpectedEntities() {
        return EXPECTED_RIDES;
    }

    @Override
    protected RideDTO createNewEntity() {
        return RideDTO.builder()
                .price(250.59)
                .distance(200.00)
                .state("completed")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .idDriver(EXPECTED_DRIVERS.getFirst().getId())
                .idUser(EXPECTED_USERS.getFirst().getId())
                .idVehicle(EXPECTED_VEHICLES.getFirst().getId())
                .build();
    }

    @Override
    protected RideDTO modifyEntity(RideDTO entity) {
        RideDTO modifiedEntity = new RideDTO(entity);
        entity.setIdDriver(EXPECTED_DRIVERS.getLast().getId());
        return modifiedEntity;
    }

    @Override
    protected Long getEntityId(RideDTO entity) {
        return entity.getId();
    }

    @Override
    protected Class<RideDTO> getEntityClass() {
        return RideDTO.class;
    }
}
