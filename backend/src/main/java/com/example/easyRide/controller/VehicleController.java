package com.example.easyRide.controller;

import com.example.easyRide.dto.VehicleDTO;
import com.example.easyRide.dto.filter.VehicleFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.easyRide.DefaultPagination.PAGINATION_SIZE;

@CrossOrigin
@RestController
@RequestMapping("api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;


    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public Page<VehicleDTO> getAllVehicles(@PageableDefault(size = PAGINATION_SIZE) Pageable pageable){
        return vehicleService.getAllVehicles(pageable);
    }

    @GetMapping(value = "/{id}")
    public VehicleDTO getVehicleById(@Valid @PathVariable("id") Long id){
        return vehicleService.getVehicleById(id);
    }

    @PostMapping(path = "create")
    public MessageBodyInfo addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO){
        return vehicleService.saveVehicle(vehicleDTO);
    }

    @DeleteMapping(path = "delete/{id}")
    public MessageBodyInfo deleteVehicle(@Valid @PathVariable("id") Long id){
        return vehicleService.deleteVehicle(id);
    }

    @PutMapping(path = "update")
    public MessageBodyInfo updateVehicle(@Valid @RequestBody VehicleDTO vehicleDTO){
        return vehicleService.updateVehicle(vehicleDTO);
    }

    @GetMapping(path = "/filter")
    public Page<VehicleDTO> filterVehicle(@Valid @PageableDefault(size = PAGINATION_SIZE) VehicleFilterDTO vehicleFilterDTO, Pageable pageable){
        return vehicleService.filterVehicle(vehicleFilterDTO, pageable);
    }
}
