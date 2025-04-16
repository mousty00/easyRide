package com.example.easyRide.repository;

import com.example.easyRide.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface RideRepository extends JpaRepository<Ride, Long>, JpaSpecificationExecutor<Ride> {

    @Modifying
    @Query(value = "DELETE r FROM rides r WHERE id_user = :id ", nativeQuery = true)
    void deleteByUserId(Long id);

    @Modifying
    @Query(value = "DELETE FROM rides WHERE id_vehicle IN (SELECT id FROM vehicles WHERE id_driver = :id);  ", nativeQuery = true)
    void deleteByDriverId(Long id);

    @Modifying
    @Query(value = "DELETE r FROM rides r WHERE id_driver IN (SELECT id FROM drivers WHERE id_vehicle = :id)  ", nativeQuery = true)
    void deleteByVehicleId(Long id);

}
