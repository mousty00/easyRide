package com.example.easyRide.mapper;

import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements BaseMapper<User, UserDTO> {


    @Override
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setTaxIdCode(userDTO.getTaxIdCode());
        user.setRegistrationDate(userDTO.getRegistrationDate());
        user.setRides(
                userDTO.getRides().stream()
                        .map(rideId -> {
                            Ride ride = new Ride();
                            ride.setId(rideId);
                            ride.setUser(user);
                            return ride;
                        }).toList()
        );

        return user;
    }

    @Override
    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .Id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .taxIdCode(user.getTaxIdCode())
                .registrationDate(user.getRegistrationDate())
                .rides(user.getRides().stream()
                        .map(Ride::getId)
                        .toList()
                )
                .build();
    }

    @Override
    public List<User> toEntityList(List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public List<UserDTO> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
