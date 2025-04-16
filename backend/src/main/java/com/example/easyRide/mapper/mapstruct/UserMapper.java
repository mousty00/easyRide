package com.example.easyRide.mapper.mapstruct;

import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import com.example.easyRide.mapper.BaseMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface UserMapper extends BaseMapper<User, UserDTO> {
    @Override
    @Mapping(target = "rides", source = "rides", qualifiedByName = "toRideIds")
    UserDTO toDto(User user);

    @Override
    @Mapping(target = "rides", source = "rides", qualifiedByName = "toRideList")
    User toEntity(UserDTO userDTO);

    @Named("toRideIds")
    default List<Long> toRideIds(List<Ride> rides) {
        return rides.stream()
                .map(Ride::getId)
                .toList();
    }

    @Named("toRideList")
    default List<Ride> toRideList(List<Long> rides) {
        return rides.stream()
                .map(rideId -> Ride.builder()
                        .id(rideId)
                        .build()
                )
                .toList();
    }
}
