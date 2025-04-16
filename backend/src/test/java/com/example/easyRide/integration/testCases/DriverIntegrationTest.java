package com.example.easyRide.integration.testCases;


import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.integration.GenericApiTest;
import java.time.LocalDate;
import java.util.List;
import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_DRIVERS;
import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_RIDES;

public class DriverIntegrationTest extends GenericApiTest<DriverDTO, Long> {

    @Override
    protected String getBasePath() {
        return "/api/drivers";
    }

    @Override
    protected List<DriverDTO> getExpectedEntities() {
        return EXPECTED_DRIVERS;
    }

    @Override
    protected DriverDTO createNewEntity() {
        return DriverDTO.builder()
                .firstName("Marco")
                .lastName("Verdi")
                .taxIdCode("MRCALS80A15B8")
                .birthDate(LocalDate.parse("1970-01-01"))
                .vehicles(List.of())
                .rides(List.of(EXPECTED_RIDES.getLast().getId()))
                .build();
    }

    @Override
    protected DriverDTO modifyEntity(DriverDTO entity) {
        DriverDTO modifiedEntity = new DriverDTO(entity);
        modifiedEntity.setFirstName("Gialli");
        return modifiedEntity;
    }

    @Override
    protected Long getEntityId(DriverDTO entity) {
        return entity.getId();
    }

    @Override
    protected Class<DriverDTO> getEntityClass() {
        return DriverDTO.class;
    }
}
