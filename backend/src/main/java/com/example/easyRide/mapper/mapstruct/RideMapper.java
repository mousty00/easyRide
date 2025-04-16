package com.example.easyRide.mapper.mapstruct;

import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RideMapper extends BaseMapper<Ride, RideDTO> {

    @Override
    @Mapping(target = "driver", source = "idDriver", qualifiedByName = "mapToDriver")
    @Mapping(target = "vehicle", source = "idVehicle", qualifiedByName = "mapToVehicle")
    @Mapping(target = "user", source = "idUser", qualifiedByName = "mapToUser")
    Ride toEntity(RideDTO rideDTO);

    @Override
    @Mapping(target = "idDriver", source = "driver.id")
    @Mapping(target = "idVehicle", source = "vehicle.id")
    @Mapping(target = "idUser", source = "user.id")
    RideDTO toDto(Ride ride);

    @Named("mapToDriver")
    default Driver mapToDriver(Long idDriver) {
        return idDriver != null ? new Driver(idDriver) : null;
    }

    @Named("mapToUser")
    default User mapToUser(Long idUser) {
        return idUser != null ? new User(idUser) : null;
    }

    @Named("mapToVehicle")
    default Vehicle mapToVehicle(Long idVehicle) {
        return idVehicle != null ? new Vehicle(idVehicle) : null;
    }
}
