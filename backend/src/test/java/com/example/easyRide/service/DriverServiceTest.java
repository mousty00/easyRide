package com.example.easyRide.service;

import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.mapper.mapstruct.DriverMapper;
import com.example.easyRide.repository.DriverRepository;
import com.example.easyRide.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;

import static com.example.easyRide.util.GlobalVariables.FAKE_DRIVER_UPDATED;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static com.example.easyRide.service.RideServiceTest.DEFAULT_PAGEABLE;
import static com.example.easyRide.util.GlobalVariables.FAKE_DRIVER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@MockitoSettings
public class DriverServiceTest extends TestHelper<Driver, DriverDTO, Long> {

    @Spy
    private DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    public void getAllDrivers_ReturnSuccess() {
        Page<Driver> driverPage = createPageWithElements(List.of(FAKE_DRIVER));
        setupRepositoryToReturnPage(driverPage, DEFAULT_PAGEABLE, driverRepository::findAll);

        Page<DriverDTO> actualPage = driverService.getAllDrivers(DEFAULT_PAGEABLE);
        Page<DriverDTO> expectedPage = createPageWithDTOs(List.of(FAKE_DRIVER), driverMapper::toDto);

        assertThat(actualPage)
                .usingRecursiveComparison()
                .isEqualTo(expectedPage);
    }

    @Test
    public void getDriverById_ReturnSuccess() {
        setupRepositoryToReturnEntity(FAKE_DRIVER, FAKE_DRIVER.getId(), driverRepository);
        DriverDTO actualDriver = driverService.getDriverById(FAKE_DRIVER.getId());
        assertThat(actualDriver)
        .usingRecursiveComparison()
                .isEqualTo(driverMapper.toDto(FAKE_DRIVER));
    }

    @Test
    public void updateDriver_ReturnSuccess() {
        FAKE_DRIVER.setId(2L);
        lenient().when(driverRepository.save(FAKE_DRIVER)).thenReturn(FAKE_DRIVER_UPDATED);
        DriverDTO driverDTO = driverMapper.toDto(FAKE_DRIVER_UPDATED);

        System.out.println(driverDTO);
        MessageBodyInfo actual = driverService.saveDriver(driverDTO);
        MessageBodyInfo expected = MessageBodyInfo.builder()
                .message("Driver saved")
                .build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void addDriver_ReturnSuccess() {
        when(driverRepository.save(any())).thenReturn(FAKE_DRIVER);
        DriverDTO driverDTO = driverMapper.toDto(FAKE_DRIVER_UPDATED);

        System.out.println(driverDTO);
        MessageBodyInfo actual = driverService.saveDriver(driverDTO);
        MessageBodyInfo expected = MessageBodyInfo.builder()
                .message("Driver saved")
                .build();

        assertThat(actual).isEqualTo(expected);
    }
}