import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/service/data/data.service';
import {Ride} from 'src/types/types';

@Component({
  selector: 'app-rides',
  templateUrl: './rides.component.html'
})
export class RidesComponent implements OnInit {
  rides: Ride[] = [];
  filteredRides: Ride[] = []
  totalPages: number[] = [];
  selectedPage: number = 0;
  pageIndex: number = 0;
  errorMessage: string = '';

  constructor(private rideService: DataService, private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllRides();
  }

  getAllRides(pageIndex?: number) {
      if (pageIndex !== undefined) {
        this.selectedPage = pageIndex;
      }
      this.rideService.getAll<Ride>(
        'rides',
        this.totalPages,
        this.rides,
        this.errorMessage,
        () => {},
        this.selectedPage,
        pageIndex
      );
      this.filteredRides = this.rides
    }

    deleteRide(ride?: Ride) {
        this.rideService.delete(
            `rides/delete/${ride?.id}`,
            `${ride?.id}`,
            this.getAllRides.bind(this)
        )
    }
}
