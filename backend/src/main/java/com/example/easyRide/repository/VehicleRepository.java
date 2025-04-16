package com.example.easyRide.repository;

import com.example.easyRide.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    @Modifying
    @Query(value = "DELETE v FROM vehicles v WHERE id_driver = :id  ", nativeQuery = true)
    void deleteByDriverId(Long id);

}
