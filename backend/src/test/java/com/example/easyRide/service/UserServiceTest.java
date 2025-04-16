package com.example.easyRide.service;

import com.example.easyRide.auth.JwtTokenProvider;
import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.dto.auth.UserLoginDTO;
import com.example.easyRide.dto.auth.UserLoginInfoDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.User;
import com.example.easyRide.mapper.mapstruct.UserMapper;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.repository.UserRepository;
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
import java.util.Optional;

import static com.example.easyRide.service.RideServiceTest.DEFAULT_PAGEABLE;
import static com.example.easyRide.util.GlobalVariables.FAKE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MockitoSettings
class UserServiceTest extends TestHelper<User, UserDTO, Long> {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService userService;

    private static final Long TEST_ID = 1L;


    @Test
    void getAllUsers_Success() {
        Page<User> users = createPageWithElements(List.of(FAKE_USER));
        setupRepositoryToReturnPage(users, DEFAULT_PAGEABLE, userRepository::findAll);
        Page<UserDTO> expectedPage = createPageWithDTOs(List.of(FAKE_USER), userMapper::toDto);
        Page<UserDTO> actualPage = userService.getAllUsers(DEFAULT_PAGEABLE);

        assertThat(actualPage)
                .usingRecursiveComparison()
                .isEqualTo(expectedPage);
    }

    @Test
    void getUserById_Success() {
        setupRepositoryToReturnEntity(FAKE_USER, FAKE_USER.getId(), userRepository);
        UserDTO actualUser = userService.getUserById(FAKE_USER.getId());
        Assertions.assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(FAKE_USER);
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(rideRepository).deleteByUserId(TEST_ID);
        lenient().doNothing().when(rideRepository).deleteById(TEST_ID);

        MessageBodyInfo actualResponse = userService.deleteUser(TEST_ID);
        MessageBodyInfo expectedResponse = MessageBodyInfo.builder()
                .message(String.format("User with id %d deleted", TEST_ID))
                .build();

        verify(userRepository).existsById(TEST_ID);
        verify(rideRepository).deleteByUserId(TEST_ID);
        verify(userRepository).deleteById(TEST_ID);
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void saveUser_Success() {
        when(userRepository.save(FAKE_USER)).thenReturn(FAKE_USER);
        UserDTO userDTO = userMapper.toDto(FAKE_USER);

        MessageBodyInfo actual = userService.saveUser(userDTO);
        MessageBodyInfo expected = MessageBodyInfo.builder()
                .message("User saved")
                .build();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void login_Success() {
        UserLoginDTO userDTO = new UserLoginDTO("John", "Green");
        String expectedToken = "Bearer token123";

        when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(FAKE_USER));
        when(jwtTokenProvider.generateToken(any())).thenReturn(expectedToken);

        UserLoginInfoDTO result = userService.login(userDTO, jwtTokenProvider);

        assertEquals("user logged in", result.message());
        verify(userRepository).findByFirstNameAndLastName("John", "Green");
        verify(jwtTokenProvider).generateToken(userDTO);
    }
}