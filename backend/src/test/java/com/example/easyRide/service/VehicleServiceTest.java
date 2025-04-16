package com.example.easyRide.service;

import com.example.easyRide.dto.VehicleDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.mapstruct.VehicleMapper;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.repository.VehicleRepository;
import com.example.easyRide.util.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.easyRide.service.RideServiceTest.DEFAULT_PAGEABLE;
import static com.example.easyRide.util.GlobalVariables.FAKE_VEHICLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
class VehicleServiceTest extends TestHelper<Vehicle, VehicleDTO, Long> {
    
    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RideRepository rideRepository;
    
    @Spy
    private VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);
    
    @InjectMocks
    private VehicleService vehicleService;

    private static final Long TEST_ID = 1L;

    @Test
    void getAllVehicles_Success() {
        Page<Vehicle> vehicles = createPageWithElements(List.of(FAKE_VEHICLE));
        setupRepositoryToReturnPage(vehicles, DEFAULT_PAGEABLE, vehicleRepository::findAll);
        Page<VehicleDTO> expectedPage = createPageWithDTOs(List.of(FAKE_VEHICLE), vehicleMapper::toDto);
        Page<VehicleDTO> actualPage = vehicleService.getAllVehicles(DEFAULT_PAGEABLE);
        assertThat(actualPage)
                .usingRecursiveComparison()
                .isEqualTo(expectedPage);
    }

    @Test
    void getVehicleById_Success() {
        setupRepositoryToReturnEntity(FAKE_VEHICLE, FAKE_VEHICLE.getId(), vehicleRepository);
        VehicleDTO actualUser = vehicleService.getVehicleById(FAKE_VEHICLE.getId());
        Assertions.assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(vehicleMapper.toDto(FAKE_VEHICLE));
    }

    @Test
    void deleteVehicle_Success() {
        when(vehicleRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(rideRepository).deleteByVehicleId(1L);
        doNothing().when(vehicleRepository).deleteById(1L);
        MessageBodyInfo actualResponse = vehicleService.deleteVehicle(TEST_ID);
        MessageBodyInfo expectedResponse = MessageBodyInfo.builder()
                .message(String.format("Vehicle with id %d deleted", TEST_ID))
                .build();
        verify(vehicleRepository).existsById(TEST_ID);
        verify(vehicleRepository).deleteById(TEST_ID);
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void addVehicle_Success() {
        when(vehicleRepository.save(any())).thenReturn(FAKE_VEHICLE);
        VehicleDTO VehicleDTO = vehicleMapper.toDto(FAKE_VEHICLE);
        MessageBodyInfo actual = vehicleService.saveVehicle(VehicleDTO);
        MessageBodyInfo expected = MessageBodyInfo.builder()
                .message("Vehicle saved")
                .build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateVehicle_Success() {
        when(vehicleRepository.existsById(FAKE_VEHICLE.getId())).thenReturn(true);
        lenient().when(vehicleRepository.save(FAKE_VEHICLE)).thenReturn(FAKE_VEHICLE);
        VehicleDTO VehicleDTO = vehicleMapper.toDto(FAKE_VEHICLE);
        MessageBodyInfo actual = vehicleService.updateVehicle(VehicleDTO);
        MessageBodyInfo expected = MessageBodyInfo.builder()
                .message("Vehicle updated")
                .build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}