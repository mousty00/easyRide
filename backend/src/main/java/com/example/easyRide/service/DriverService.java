package com.example.easyRide.service;

import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.dto.filter.DriverFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.mapstruct.DriverMapper;
import com.example.easyRide.pagination.PageCustomImpl;
import com.example.easyRide.pagination.SliceDto;
import com.example.easyRide.repository.DriverRepository;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.repository.VehicleRepository;
import com.example.easyRide.specification.DriverSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverMapper driverMapper;
    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;
    private final VehicleRepository vehicleRepository;


    public Page<DriverDTO> getAllDrivers(Pageable pageable) {
        final Page<Driver> drivers = driverRepository.findAll(pageable);
        Page<DriverDTO> map = drivers.map(driverMapper::toDto);
        return new PageCustomImpl<>(map.getContent(), pageable, drivers.getTotalElements());
    }

    @Transactional
    public DriverDTO getDriverById(Long id) {
        return driverMapper.toDto(driverRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Driver with id %d not found", id))
                )
        );
    }

    @Transactional
    public MessageBodyInfo saveDriver(DriverDTO driverDTO) {
        Driver save = driverRepository.save(driverMapper.toEntity(driverDTO));
        return MessageBodyInfo.builder().message("Driver saved").build();
    }

    @Transactional
    public MessageBodyInfo updateDriver(DriverDTO driverDTO) {
        List<Ride> rides = driverDTO.getRides().stream().map(
                ride -> rideRepository.findById(ride)
                        .orElseThrow(
                                () -> new NoSuchElementException(String.format("Ride with id %d not found", ride))
                        )
        ).toList();

        List<Vehicle> vehicles = driverDTO.getVehicles().stream().map(
                vehicle -> vehicleRepository.findById(vehicle)
                        .orElseThrow(
                                ()-> new NoSuchElementException(String.format("vehicle with id %d not found", vehicle))
                        )
        ).toList();

        Driver driver = driverMapper.toEntity(driverDTO);
        driver.setRides(rides);
        driver.setVehicles(vehicles);

        driverRepository.save(driver);

        return MessageBodyInfo.builder().message("Driver updated").build();
    }

    @Transactional
    public MessageBodyInfo deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Driver with id %d not found", id));
        } else {
            rideRepository.deleteByDriverId(id);
            vehicleRepository.deleteByDriverId(id);
            driverRepository.deleteById(id);
            return MessageBodyInfo.builder().message(String.format("Driver with id %d deleted", id)).build();
        }
    }

    public List<DriverDTO> getTwo(Long aLong) {
        return driverMapper.toDtoList(driverRepository.getTwoJPQL(aLong));
    }

    public List<DriverDTO> getTwoNativeQuery(Long aLong) {
        return driverMapper.toDtoList(driverRepository.getTwoNativeQuery(aLong));
    }

    public SliceDto<DriverDTO> filterDriver(DriverFilterDTO driverFilterDTO, Pageable pageable, String url) {
        Slice<Driver> drivers = driverRepository.findAll(DriverSpecification.filterDriver(driverFilterDTO), pageable);
        Slice<DriverDTO> map = drivers.map(driverMapper::toDto);
        return new SliceDto<>(map, url);
    }
}
