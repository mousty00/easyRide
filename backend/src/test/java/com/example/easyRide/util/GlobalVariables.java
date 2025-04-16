package com.example.easyRide.util;

import com.example.easyRide.entity.Driver;
import com.example.easyRide.entity.Ride;
import com.example.easyRide.entity.User;
import com.example.easyRide.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;

public class GlobalVariables {

    static List<Ride> rides = List.of();
    public static Ride FAKE_RIDE = Ride.builder()
            .id(1L)
            .price(203.50)
            .state("pending")
            .startDate(LocalDate.parse("2024-10-10"))
            .endDate(LocalDate.parse("2024-10-10"))
            .distance(40.00)
            .driver(new Driver(1L))
            .vehicle(new Vehicle(1L))
            .user(new User(1L, "John", "Green", LocalDate.parse("1998-10-10"), "JHNGRN1234ABC", LocalDate.now(), rides))
            .build();

    public static Driver FAKE_DRIVER = new Driver(
            1L,
            "Marco",
            "Rossi",
            LocalDate.parse("2000-10-10"),
            "MRCRSI152436MTP",
            List.of(),
            List.of(FAKE_RIDE)
    );

    public static Driver FAKE_DRIVER_UPDATED = new Driver(
            1L,
            "John",
            "White",
            LocalDate.parse("2000-10-16"),
            "MRCRSI152436MTP",
            List.of(),
            List.of(FAKE_RIDE)
    );

    public static Vehicle FAKE_VEHICLE = new Vehicle(
            1L,
            "Fiat Panda",
            "MSMA3023",
            "Red",
            FAKE_DRIVER
    );

    public static User FAKE_USER = new User(
            1L, "John", "Green", LocalDate.parse("1998-10-10"),
            "JHNGRN1234ABC", LocalDate.now(), rides);


}
