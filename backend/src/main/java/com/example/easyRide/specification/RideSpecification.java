package com.example.easyRide.specification;

import com.example.easyRide.dto.filter.RideFilterDTO;
import com.example.easyRide.entity.Ride;
import org.springframework.data.jpa.domain.Specification;

public class RideSpecification {

    public static Specification<Ride> hasId(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("id"), rideFilterDTO.id());
    }

    public static Specification<Ride> hasState(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.like(root.get("state"),  "%" + rideFilterDTO.state() + '%');
    }

    public static Specification<Ride> hasDistance(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("distance"), rideFilterDTO.distance());
    }

    public static Specification<Ride> hasStartDate(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("startDate"), rideFilterDTO.startDate());
    }

    public static Specification<Ride> hasEndDate(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("endDate"), rideFilterDTO.endDate());
    }

    public static Specification<Ride> hasDriver(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("idDriver"), rideFilterDTO.idDriver());
    }

    public static Specification<Ride> hasUser(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("idUser"), rideFilterDTO.idUser());
    }

    public static Specification<Ride> hasVehicle(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("idVehicle"), rideFilterDTO.idVehicle());
    }

    private static Specification<Ride> hasPrice(RideFilterDTO rideFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), rideFilterDTO.price());
    }

    public static Specification<Ride> filterRide(RideFilterDTO rideFilterDTO) {
        return Specification.where(rideFilterDTO.id() != null ? RideSpecification.hasId(rideFilterDTO) : null)
                .and(rideFilterDTO.state() != null ? RideSpecification.hasState(rideFilterDTO) : null)
                .and(RideSpecification.hasDistance(rideFilterDTO))
                .and(rideFilterDTO.startDate() != null ? RideSpecification.hasStartDate(rideFilterDTO) : null)
                .and(rideFilterDTO.endDate() != null ? RideSpecification.hasEndDate(rideFilterDTO) : null)
                .and(rideFilterDTO.idUser() != null ? RideSpecification.hasUser(rideFilterDTO) : null)
                .and(rideFilterDTO.price() != null ? RideSpecification.hasPrice(rideFilterDTO) : null)
                .and(rideFilterDTO.idDriver() != null ? RideSpecification.hasDriver(rideFilterDTO) : null)
                .and(rideFilterDTO.idVehicle() != null ? RideSpecification.hasVehicle(rideFilterDTO) : null);
    }
}
