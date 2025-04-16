package com.example.easyRide.specification;

import com.example.easyRide.dto.filter.DriverFilterDTO;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Driver_;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.Vehicle;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class DriverSpecification {

    public static Specification<Driver> hasFirstName(DriverFilterDTO driverFilterDTO) {
        return (root, query, cb) ->
                driverFilterDTO
                        .firstName() != null
                        ? cb.like(root.get("firstName"),  "%" + driverFilterDTO.firstName() + "%")
                        : cb.conjunction();
    }

    public static Specification<Driver> hasLastName(DriverFilterDTO driverFilterDTO) {
        return (root, query, cb) ->
                driverFilterDTO.lastName() != null
                        ? cb.like(root.get(Driver_.LAST_NAME),  "%" + driverFilterDTO.lastName() + "%")
                        : cb.conjunction();
    }

    public static Specification<Driver> hasTaxIdCode(DriverFilterDTO driverFilterDTO) {
        return (root, query, cb) ->
                driverFilterDTO.taxIdCode() != null
                        ? cb.like(root.get("taxIdCode"),  "%" + driverFilterDTO.taxIdCode() + "%")
                        : cb.conjunction();
    }

    public static Specification<Driver> hasId(DriverFilterDTO driverFilterDTO) {
        return (root, query, cb) ->
                driverFilterDTO.id() != null
                        ? cb.equal(root.get("id"), driverFilterDTO.id())
                        : cb.conjunction();
    }

    public static Specification<Driver> hasRides(DriverFilterDTO driverDTO) {
        return (root, query, cb) -> {
            if (driverDTO.rides() != null && !driverDTO.rides().isEmpty()) {
                Join<Driver, Ride> rideJoin = root.join("rides");
                return rideJoin.get("id").in(driverDTO.rides());
            }
            return cb.conjunction();
        };
    }

    public static Specification<Driver> hasVehicle(DriverFilterDTO driverFilterDTO) {
        return (root, query, cb) -> {
            if (driverFilterDTO.vehicles() != null && !driverFilterDTO.vehicles().isEmpty()) {
                Join<Driver, Vehicle> vehicleJoin = root.join("vehicles");
                return vehicleJoin.get("id").in(driverFilterDTO.vehicles());
            }
            return cb.conjunction();
        };
    }

    public static Specification<Driver> filterDriver(DriverFilterDTO driverFilterDTO) {
        return Specification.where(driverFilterDTO.id() != null ? hasId(driverFilterDTO) : null)
                .and(driverFilterDTO.firstName() != null ? hasFirstName(driverFilterDTO) : null)
                .and(driverFilterDTO.lastName() != null ? hasLastName(driverFilterDTO) : null)
                .and(driverFilterDTO.taxIdCode() != null ? hasTaxIdCode(driverFilterDTO) : null)
                .and(driverFilterDTO.rides() != null ? hasRides(driverFilterDTO) : null)
                .and(driverFilterDTO.vehicles() != null ? hasVehicle(driverFilterDTO) : null);
    }
}