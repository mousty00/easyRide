package com.example.easyRide.controller;

import com.example.easyRide.ServerUrl;
import com.example.easyRide.auth.JwtTokenProvider;
import com.example.easyRide.dto.UserDTO;
import com.example.easyRide.dto.auth.UserLoginDTO;
import com.example.easyRide.dto.auth.UserLoginInfoDTO;
import com.example.easyRide.dto.filter.UserFilterDto;
import com.example.easyRide.dto.info.MessageBodyInfo;
import com.example.easyRide.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.easyRide.DefaultPagination.PAGINATION_SIZE;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(ServerUrl serverUrl, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(@PageableDefault(size = PAGINATION_SIZE) final Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUserById(@Valid @PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "create")
    public MessageBodyInfo addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PutMapping(path = "update")
    public MessageBodyInfo updateUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping(path = "delete/{id}")
    public MessageBodyInfo deleteUser(@Valid @PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping(path = "/filter")   
    public Page<UserDTO> filterUser(@PageableDefault(size = PAGINATION_SIZE) UserFilterDto userFilterDTO, final Pageable pageable) {
        return userService.filterUser(userFilterDTO, pageable);
    }

    @PostMapping("/login")
    public UserLoginInfoDTO login(@Valid @RequestBody UserLoginDTO userDTO) {
        return userService.login(userDTO, jwtTokenProvider);
    }
}
