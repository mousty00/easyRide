package com.example.easyRide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String taxIdCode;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.MERGE)
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.MERGE)
    private List<Ride> rides;


    public Driver(Long id) {
        this.id = id;
    }

}
