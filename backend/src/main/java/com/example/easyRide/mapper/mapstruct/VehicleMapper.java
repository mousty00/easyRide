package com.example.easyRide.mapper.mapstruct;

import com.example.easyRide.dto.VehicleDTO;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.BaseMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface VehicleMapper extends BaseMapper<Vehicle, VehicleDTO> {

    @Override
    @Mapping(target = "idDriver", source = "driver.id")
    VehicleDTO toDto(Vehicle vehicle);

    @Override
    @Mapping(target = "driver", source = "idDriver", qualifiedByName = "mapToDriver")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    @Named("mapToDriver")
    default Driver mapToDriver(Long idDriver) {
        return idDriver != null ? new Driver(idDriver) : new Driver(0L);
    }

    @Override
    List<Vehicle> toEntityList(List<VehicleDTO> vehicleDTOS);

    @Override
    List<VehicleDTO> toDtoList(List<Vehicle> vehicles);
}
