import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/service/data/data.service';
import {Vehicle} from 'src/types/types';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
})
export class VehiclesComponent implements OnInit {
  vehicles: Vehicle[] = [];
  filteredVehicles: Vehicle[] = [];
  totalPages: number[] = [];
  selectedPage: number = 0;
  pageIndex: number = 0;
  errorMessage: string = '';

  constructor(private http: HttpClient, private vehicleService: DataService) {}

  ngOnInit(): void {
    this.getAllVehicles();
  }

  getAllVehicles(pageIndex?: number) {
    if (pageIndex !== undefined) {
      this.selectedPage = pageIndex;
    }
    this.vehicleService.getAll<Vehicle>(
      'vehicles',
      this.totalPages,
      this.vehicles,
      this.errorMessage,
      () => {},
      this.selectedPage,
      pageIndex
    );
    this.filteredVehicles = this.vehicles;
  }

  deleteVehicle(vehicle: Vehicle) {
    this.vehicleService.delete(
      `vehicles/delete/${vehicle.id}`,
      vehicle.model,
      this.getAllVehicles
    );
  }
}
