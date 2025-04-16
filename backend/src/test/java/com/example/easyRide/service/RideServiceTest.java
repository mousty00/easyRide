package com.example.easyRide.service;

import com.example.easyRide.util.TestHelper;
import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.RideDetailView;
import com.example.easyRide.mapper.mapstruct.RideMapper;
import com.example.easyRide.repository.RideDetailViewRepository;
import com.example.easyRide.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.easyRide.DefaultPagination.PAGINATION_SIZE;
import static com.example.easyRide.util.GlobalVariables.FAKE_RIDE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@MockitoSettings
public class RideServiceTest extends TestHelper<Ride, RideDTO, Long> {
    @Mock
    private RideRepository rideRepository;
    @Spy
    private RideMapper rideMapper = Mappers.getMapper(RideMapper.class);
    @Mock
    private RideDetailViewRepository rideDetailViewRepository;
    @InjectMocks
    private RideService rideService;

    protected static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, PAGINATION_SIZE);
    private static final Long TEST_ID = 1L;

//    @ParameterizedTest
//    @CsvSource({
//            "sdawd", "46",
//            "adawda", "903"
//    })
    @Test
    void getAllRides() {
        Page<Ride> expectedPage = createPageWithElements(List.of(FAKE_RIDE));
        setupRepositoryToReturnPage(expectedPage, DEFAULT_PAGEABLE, rideRepository::findAll);

        Page<RideDTO> actual = rideService.getAllRides(DEFAULT_PAGEABLE);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(createPageWithDTOs(List.of(FAKE_RIDE),rideMapper::toDto));
    }

    @Test
    void getExistingRideById_ReturnRide() {
        setupRepositoryToReturnEntity(FAKE_RIDE, FAKE_RIDE.getId(), rideRepository);
        RideDTO actual = rideService.getRideById(TEST_ID);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(rideMapper.toDto(FAKE_RIDE));
    }

    @Test
    void deleteExistingRide_ReturnSuccessMessage() {
        when(rideRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(rideRepository).deleteById(TEST_ID);

        MessageBodyInfo actualResponse = rideService.deleteRide(TEST_ID);
        MessageBodyInfo expectedResponse = MessageBodyInfo.builder()
                .message(String.format("Ride with id %d deleted", TEST_ID))
                .build();

        verify(rideRepository).existsById(TEST_ID);
        verify(rideRepository).deleteById(TEST_ID);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void saveExistingRide_ReturnSuccessMessage() {
        when(rideRepository.save(any())).thenReturn(FAKE_RIDE);
        RideDTO rideDTO = rideMapper.toDto(FAKE_RIDE);

        MessageBodyInfo actualResponse = rideService.saveRide(rideDTO);
        MessageBodyInfo expectedResponse = MessageBodyInfo.builder()
                .message("Ride saved")
                .build();
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getAllRideDetails_ReturnList() {
        List<RideDetailView> expected = List.of(new RideDetailView());
        when(rideDetailViewRepository.findAll()).thenReturn(expected);

        List<RideDetailView> actual = rideService.getAllFromView();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
