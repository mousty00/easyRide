package com.example.easyRide.mapper.mapstruct;

import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.BaseMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel =  SPRING, builder = @Builder(disableBuilder = true))
public interface DriverMapper extends BaseMapper<Driver, DriverDTO> {


    @Override
    @Mapping(target = "rides", source = "rides", qualifiedByName = "toRideIds")
    @Mapping(target = "vehicles", source = "vehicles", qualifiedByName = "toVehicleIds")
    DriverDTO toDto(Driver driver);

    @Override
    @Mapping(target = "rides", source = "rides", qualifiedByName = "toRideList")
    @Mapping(target = "vehicles", source = "vehicles", qualifiedByName = "toVehicleList")
    Driver toEntity(DriverDTO driverDTO);

    @Named("toRideIds")
    default List<Long> toRideIds(List<Ride> rides) {
        return rides.stream()
                .map(Ride::getId)
                .toList();
    }

    @Named("toRideList")
    default List<Ride> toRideList(List<Long> rideIds) {
        return rideIds.stream()
                .map(id -> Ride.builder()
                        .id(id)
                        .build()
                )
                .toList();
    }

    @Named("toVehicleIds")
    default List<Long> toVehicleIds(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getId).
                toList();
    }

    @Named("toVehicleList")
    default List<Vehicle> toVehicleList(List<Long> vehicleIds) {
        return vehicleIds.stream()
                .map(id -> Vehicle.builder()
                        .id(id)
                        .build()
                )
                .toList();
    }
}
