package com.example.easyRide.specification;

import com.example.easyRide.dto.filter.VehicleFilterDTO;
import com.example.easyRide.entity.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> hasId(VehicleFilterDTO vehicleFilterDTO) {
        return ((root, query, cb) ->
                cb.equal(root.get("id"), vehicleFilterDTO.id()));
    }

    public static Specification<Vehicle> hasModel(VehicleFilterDTO vehicleFilterDTO) {
        return (root, query, cb) ->
                cb.like(root.get("model"),  "%" + vehicleFilterDTO.model() + "%");
    }

    public static Specification<Vehicle> hasColor(VehicleFilterDTO vehicleFilterDTO) {
        return (root, query, cb) ->
                cb.like(root.get("color"),  "%" + vehicleFilterDTO.color() + "%");
    }

    public static Specification<Vehicle> hasPlateNumber(VehicleFilterDTO vehicleFilterDTO) {
        return (root, query, cb) ->
                cb.like(root.get("plateNumber"),  "%" + vehicleFilterDTO.plateNumber() + "%");
    }

    public static Specification<Vehicle> hasDriver(VehicleFilterDTO vehicleFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("idDriver"), vehicleFilterDTO.idDriver());
    }

    public static Specification<Vehicle> filterVehicle(VehicleFilterDTO vehicleFilterDTO) {
        return Specification.where(vehicleFilterDTO.id() != null ? hasId(vehicleFilterDTO) : null)
                .and(vehicleFilterDTO.model() != null ? hasModel(vehicleFilterDTO) : null)
                .and(vehicleFilterDTO.color() != null ? hasColor(vehicleFilterDTO) : null)
                .and(vehicleFilterDTO.plateNumber() != null ? hasPlateNumber(vehicleFilterDTO) : null)
                .and(vehicleFilterDTO.idDriver() != null ? hasDriver(vehicleFilterDTO) : null);
    }
}
