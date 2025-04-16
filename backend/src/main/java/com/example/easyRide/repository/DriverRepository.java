package com.example.easyRide.repository;

import com.example.easyRide.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
    @Query("SELECT d FROM Driver d WHERE d.id < :id")
    List<Driver> getTwoJPQL(Long id);

    @Query(nativeQuery = true, value  = "SELECT * FROM drivers d WHERE d.id > ?1")
    List<Driver> getTwoNativeQuery(Long id);
}
