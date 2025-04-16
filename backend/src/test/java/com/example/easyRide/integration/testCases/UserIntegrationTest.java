package com.example.easyRide.integration.testCases;

import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.integration.GenericApiTest;

import java.time.LocalDate;
import java.util.List;

import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_RIDES;
import static com.example.easyRide.integration.testCases.TestVariables.EXPECTED_USERS;

public class UserIntegrationTest extends GenericApiTest<UserDTO, Long> {

    @Override
    protected String getBasePath() {
        return "/api/users";
    }

    @Override
    protected List<UserDTO> getExpectedEntities() {
        return EXPECTED_USERS;
    }

    @Override
    protected UserDTO createNewEntity() {
        return UserDTO.builder()
                .firstName("John")
                .lastName("White")
                .taxIdCode("JHNWTE7319JHN")
                .birthDate(LocalDate.parse("1990-01-01"))
                .registrationDate(LocalDate.now())
                .rides(List.of(EXPECTED_RIDES.getFirst().getId()))
                .build();
    }

    @Override
    protected UserDTO modifyEntity(UserDTO entity) {
        UserDTO modifiedUser = new UserDTO(entity);
        modifiedUser.setLastName("Ndiayee");
        System.out.println(modifiedUser);
        return modifiedUser;
    }

    @Override
    protected Long getEntityId(UserDTO entity) {
        UserDTO userDto = new UserDTO(entity);
        return userDto.getId();
    }

    @Override
    protected Class<UserDTO> getEntityClass() {
        return UserDTO.class;
    }
}