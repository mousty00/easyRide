package com.example.easyRide.service;

import com.example.easyRide.auth.JwtTokenProvider;
import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.dto.auth.UserLoginDTO;
import com.example.easyRide.dto.auth.UserLoginInfoDTO;
import com.example.easyRide.dto.filter.UserFilterDto;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import com.example.easyRide.mapper.mapstruct.UserMapper;
import com.example.easyRide.pagination.PageCustomImpl;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.repository.UserRepository;
import com.example.easyRide.specification.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RideRepository rideRepository;

    public Page<UserDTO> getAllUsers(final Pageable pageable) {
        final Page<User> users = userRepository.findAll(pageable);
        Page<UserDTO> map = users.map(userMapper::toDto);
        return new PageCustomImpl<>(map.getContent(), pageable, map.getTotalElements());
    }

    public UserDTO getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("User with id %d not found", id))
                )
        );
    }

    @Transactional
    public MessageBodyInfo deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("User with id %d not found", id));
        } else {
            rideRepository.deleteByUserId(id);
            userRepository.deleteById(id);
            return MessageBodyInfo.builder()
                    .message(String.format("User with id %d deleted", id))
                    .build();
        }
    }

    @Transactional
    public MessageBodyInfo saveUser(UserDTO userDTO) {
        final User user;
        try {
            user = userRepository.save(userMapper.toEntity(userDTO));
            System.out.printf("user saved %s", user);
            return MessageBodyInfo.builder().message("User saved").build();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("tax id already exists");
        }
    }

    public MessageBodyInfo updateUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getId())) {
            userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("User with id %d not found", userDTO.getId())));
            List<Ride> rides = userDTO.getRides().stream()
                    .map(ride -> rideRepository.findById(ride)
                            .orElseThrow(() ->
                                    new NoSuchElementException(String.format("Ride with id %d not found", ride)))
                    )
                    .toList();

            User user = userMapper.toEntity(userDTO);
            user.setRides(rides);
            userRepository.save(user);

            return MessageBodyInfo.builder().message("User updated").build();
        }
        throw new NoSuchElementException(String.format("User with id %d not found", userDTO.getId()));
    }

    public Page<UserDTO> filterUser(UserFilterDto userFilterDTO, Pageable pageable) {
        Page<User> users = userRepository.findAll(UserSpecification.filterUser(userFilterDTO), pageable);
        Page<UserDTO> map = users.map(userMapper::toDto);
        return new PageCustomImpl<>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    public UserLoginInfoDTO login(UserLoginDTO userDTO, JwtTokenProvider jwtTokenProvider) {
        User user = userRepository.findByFirstNameAndLastName(userDTO.getFirstName(), userDTO.getLastName())
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("User with name %s not found", userDTO.getFirstName()))
                );
        String msg = "user logged in";
        return new UserLoginInfoDTO(msg, jwtTokenProvider.generateToken(userDTO), userMapper.toDto(user));
    }
}
