package com.example.easyRide.controller;

import com.example.easyRide.ServerUrl;
import com.example.easyRide.dto.DriverDTO;
import com.example.easyRide.dto.filter.DriverFilterDTO;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.pagination.SliceDto;
import com.example.easyRide.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.easyRide.DefaultPagination.PAGINATION_SIZE;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    private final DriverService driverService;

    private final ServerUrl serverUrl;

    public DriverController(DriverService driverService, ServerUrl serverUrl) {
        this.driverService = driverService;
        this.serverUrl = serverUrl;
    }


    @GetMapping
    public Page<DriverDTO> getAllDrivers(@PageableDefault(size = PAGINATION_SIZE) Pageable pageable) {
        return driverService.getAllDrivers(pageable);
    }

    @GetMapping(value = "/{id}")
    public DriverDTO getDriverById(@Valid @PathVariable String id) {
        return driverService.getDriverById(Long.valueOf(id));
    }

    @PostMapping(path = "create")
    public MessageBodyInfo addDriver(@Valid @RequestBody DriverDTO driverDTO) {
        return driverService.saveDriver(driverDTO);
    }

    @PutMapping(path = "update")
    public MessageBodyInfo updateDriver(@Valid @RequestBody DriverDTO driverDTO) {
        return driverService.updateDriver(driverDTO);
    }

    @DeleteMapping(path = "delete/{id}")
    public MessageBodyInfo deleteDriver(@Valid @PathVariable String id) {
        return driverService.deleteDriver(Long.valueOf(id));
    }

    @GetMapping(path = "/queryLessThen")
    public List<DriverDTO> getIdLessThen(@Valid @RequestParam String id) {
        return driverService.getTwo(Long.valueOf(id));
    }

    @GetMapping(path = "/queryGreaterThen")
    public List<DriverDTO> getIdGreaterThen(@Valid @RequestParam String id) {
        return driverService.getTwoNativeQuery(Long.valueOf(id));
    }

    @GetMapping(path = "/filter")
    public SliceDto<DriverDTO> filterDriver(@PageableDefault(size = PAGINATION_SIZE) DriverFilterDTO driverFilterDTO, Pageable pageable) {
        return driverService.filterDriver(driverFilterDTO, pageable, serverUrl.getUrl() + "/api/drivers/filter");
    }

}
