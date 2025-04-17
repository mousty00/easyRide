import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/service/data/data.service';
import { UserService } from 'src/app/service/user/user.service';
import {Ride, Vehicle} from 'src/types/types';
import Swal from "sweetalert2";

@Component({
  selector: 'app-rides',
  templateUrl: './rides.component.html',
})
export class RidesComponent implements OnInit {
  rides: Ride[] = [];
  filteredRides: Ride[] = [];
  totalPages: number[] = [];
  selectedPage: number = 0;
  pageIndex: number = 0;
  errorMessage: string = '';
  editable: boolean = false;

  constructor(
    private rideService: DataService,
    private userService: UserService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.getAllRides();
  }

  getAllRides(pageIndex?: number) {
    if (pageIndex !== undefined) {
      this.selectedPage = pageIndex;
    }
    this.rideService.sendRequestData('rides',this.http.get).subscribe({
      next: (response)=> {
        if (this.totalPages.length == 0) {
          for (let i = 0; i < response.totalPages; i++) {
            this.totalPages.push(i);
          }
        }
        this.selectedPage = pageIndex!;
        if (response.results) {
          this.rides = response.results
          this.rides.forEach((ride) => {
            this.userService.getUserById(ride.idUser).subscribe({
              next: (response) => {
                ride.user = response;
              }
            })
            this.rideService.sendRequestData(`drivers/${ride.idDriver}`, this.http.get).subscribe({
              next: (response)=> {
                ride.driver = response
              }
            })
            this.rideService.sendRequestData(`vehicles/${ride.idVehicle}`, this.http.get)
              .subscribe({
                next: (vehicle: Vehicle) => {
                  ride.vehicle = vehicle
                }
              })
          })
        } else {
          Swal.fire({
            title: 'list empty',
            icon: 'info',
            draggable: true,
          });
        }
    }
    })

    this.filteredRides = this.rides;
  }


  deleteRide(ride?: Ride) {
    this.rideService.delete(
      `rides/delete/${ride?.id}`,
      `${ride?.id}`,
      this.getAllRides.bind(this)
    );
  }
}
