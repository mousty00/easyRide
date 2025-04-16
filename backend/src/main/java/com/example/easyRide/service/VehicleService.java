package com.example.easyRide.service;

import com.example.easyRide.dto.filter.VehicleFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.dto.VehicleDTO;
import com.example.easyRide.entity.Vehicle;
import com.example.easyRide.mapper.mapstruct.VehicleMapper;
import com.example.easyRide.pagination.PageCustomImpl;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.repository.VehicleRepository;
import com.example.easyRide.specification.VehicleSpecification;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final RideRepository rideRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, RideRepository rideRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.rideRepository = rideRepository;
    }

    @Transactional
    public Page<VehicleDTO> getAllVehicles(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        Page<VehicleDTO> map = vehicles.map(vehicleMapper::toDto);
        return new PageCustomImpl<>(map.getContent(),map.getPageable(), map.getTotalElements());
    }

    @Transactional
    public VehicleDTO getVehicleById(Long id) {
        return vehicleMapper.toDto(vehicleRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Vehicle with id %d not found", id))
                )
        );
    }

    @Transactional
    public MessageBodyInfo deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Vehicle with id %d not found", id));
        } else {
            rideRepository.deleteByVehicleId(id);
            vehicleRepository.deleteById(id);
            return MessageBodyInfo.builder().message(String.format("Vehicle with id %d deleted", id)).build();
        }
    }

    @Transactional
    public MessageBodyInfo saveVehicle(VehicleDTO vehicleDTO) {
        try {
            vehicleRepository.save(vehicleMapper.toEntity(vehicleDTO));
            return MessageBodyInfo.builder().message("Vehicle saved").build();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("plate Number already exists");
        }
    }

    @Transactional
    public MessageBodyInfo updateVehicle(VehicleDTO vehicleDTO) {
        if(vehicleRepository.existsById(vehicleDTO.getId())) {
            try {
                vehicleRepository.save(vehicleMapper.toEntity(vehicleDTO));
                return MessageBodyInfo.builder().message("Vehicle updated").build();
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("plate Number already exists");
            }
        }
        throw new NoSuchElementException(String.format("vehicle with id %d not found", vehicleDTO.getId()));
    }

    @Transactional
    public Page<VehicleDTO> filterVehicle(VehicleFilterDTO vehicleFilterDTO, Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(VehicleSpecification.filterVehicle(vehicleFilterDTO), pageable);
        Page<VehicleDTO> map = vehicles.map(vehicleMapper::toDto);
        return new PageCustomImpl<>(map.getContent(),map.getPageable(), map.getTotalElements());
    }
}
