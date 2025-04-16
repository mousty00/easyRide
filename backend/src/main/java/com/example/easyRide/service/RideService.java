package com.example.easyRide.service;

import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.dto.filter.RideFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.RideDetailView;
import com.example.easyRide.mapper.mapstruct.RideMapper;
import com.example.easyRide.pagination.PageCustomImpl;
import com.example.easyRide.repository.RideDetailViewRepository;
import com.example.easyRide.repository.RideRepository;
import com.example.easyRide.specification.RideSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class RideService {
    private final RideRepository rideRepository;
    private final RideDetailViewRepository rideDetailViewRepository;
    private final RideMapper rideMapper;


    public RideService(RideRepository rideRepository,RideDetailViewRepository rideDetailViewRepository, RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideDetailViewRepository = rideDetailViewRepository;
        this.rideMapper = rideMapper;
    }

    public Page<RideDTO> getAllRides(final Pageable pageable) {
        final Page<Ride> rides = rideRepository.findAll(pageable);
        final Page<RideDTO> map = rides.map(rideMapper::toDto);
        return new PageCustomImpl<>(map.getContent(), map.getPageable(), map.getTotalElements());
    }


    public RideDTO getRideById(Long id) {
        return rideMapper.toDto(rideRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Ride with id %d not found", id))
                )
        );
    }

    public MessageBodyInfo deleteRide(Long id) {
        if (!rideRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Ride with id %d not found", id));
        } else {
            rideRepository.deleteById(id);
            return MessageBodyInfo.builder().message(String.format("Ride with id %d deleted", id)).build();
        }
    }

    public MessageBodyInfo saveRide(RideDTO rideDTO) {
        rideRepository.save(rideMapper.toEntity(rideDTO));
        return MessageBodyInfo.builder().message("Ride saved").build();
    }

    public MessageBodyInfo updateRide(RideDTO rideDTO) {
        rideRepository.save(rideMapper.toEntity(rideDTO));
        return MessageBodyInfo.builder().message("Ride updated").build();
    }

    public Page<RideDTO> filterRide(RideFilterDTO rideFilterDTO, Pageable pageable) {
        Page<Ride> rides = rideRepository.findAll(RideSpecification.filterRide(rideFilterDTO), pageable);
        Page<RideDTO> map = rides.map(rideMapper::toDto);
        return new PageCustomImpl<>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    public List<RideDetailView> getAllFromView(){
        return rideDetailViewRepository.findAll();
    }
}
