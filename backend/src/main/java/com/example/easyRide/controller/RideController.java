package com.example.easyRide.controller;

import com.example.easyRide.dto.RideDTO;
import com.example.easyRide.dto.filter.RideFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.entity.RideDetailView;
import com.example.easyRide.service.RideService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.easyRide.DefaultPagination.PAGINATION_SIZE;

@RestController
@RequestMapping("api/rides")
public class RideController {

    private final RideService rideService;


    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping
    public Page<RideDTO> getAllRides(@PageableDefault(size = PAGINATION_SIZE) Pageable pageable) {
        return rideService.getAllRides(pageable);
    }

    @GetMapping(value = "/{id}")
    public RideDTO getRideById(@Valid @PathVariable("id") Long id) {
        return rideService.getRideById(id);
    }

    @PostMapping(path = "create")
    public MessageBodyInfo addRide(@Valid @RequestBody RideDTO rideDTO) {
        return rideService.saveRide(rideDTO);
    }

    @PutMapping(path = "update")
    public MessageBodyInfo updateRide(@Valid @RequestBody RideDTO rideDTO) {
        return rideService.updateRide(rideDTO);
    }

    @DeleteMapping(path = "delete/{id}")
    public MessageBodyInfo deleteRide(@Valid @PathVariable("id") Long id) {
        return rideService.deleteRide(id);
    }

    @GetMapping(path = "/filter")
    public Page<RideDTO> filterRide(@PageableDefault(size = PAGINATION_SIZE) RideFilterDTO rideFilterDTO, Pageable pageable) {
        return rideService.filterRide(rideFilterDTO, pageable);
    }

    @GetMapping(path = "/detail")
    public List<RideDetailView> getRideDetail() {
        return rideService.getAllFromView();
    }
}
