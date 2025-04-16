package com.example.easyRide.specification;

import com.example.easyRide.dto.filter.UserFilterDto;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasName(UserFilterDto userFilterDTO) {
        return (root, query, cb)
                -> cb.like(root.get("firstName"),   "%" + userFilterDTO.firstName() + "%");
    }

    public static Specification<User> hasLastName(UserFilterDto userFilterDTO) {
        return (root, query, cb)
                -> cb.like(root.get("lastName"),  "%" + userFilterDTO.lastName() + "%");
    }

    public static Specification<User> hasBirthDate(UserFilterDto userFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("birthDate"), userFilterDTO.birthDate());
    }

    public static Specification<User> hasTaxIdCode(UserFilterDto userFilterDTO) {
        return (root, query, cb) ->
                cb.like(root.get("taxIdCode"),  "%" + userFilterDTO.taxIdCode() + "%");
    }

    public static Specification<User> hasRegistrationDate(UserFilterDto userFilterDTO) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("registrationDate"), userFilterDTO.registrationDate());
    }

    public static Specification<User> hasId(UserFilterDto userFilterDTO) {
        return (root, query, cb) ->
                cb.equal(root.get("id"), userFilterDTO.Id());
    }

    public static Specification<User> hasRides(UserFilterDto userFilterDTO) {
        return (root, query, cb) -> {
            Join<User, Ride> ridejoin = root.join("rides");
            if (ridejoin.get("id") != null) {
                return ridejoin.get("id").in(userFilterDTO.rides());
            } else {
                return null;
            }
        };
    }

    public static Specification<User> filterUser(UserFilterDto userFilterDTO) {
        return Specification.where(userFilterDTO.firstName() != null ? hasName(userFilterDTO) : null)
                .and(userFilterDTO.lastName() != null ? hasLastName(userFilterDTO) : null)
                .and(userFilterDTO.birthDate() != null ? hasBirthDate(userFilterDTO) : null)
                .and(userFilterDTO.taxIdCode() != null ? hasTaxIdCode(userFilterDTO) : null)
                .and(userFilterDTO.rides() != null ? hasRides(userFilterDTO) : null)
                .and(userFilterDTO.registrationDate() != null ? hasRegistrationDate(userFilterDTO) : null);
    }
}
