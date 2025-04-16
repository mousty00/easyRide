import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/service/data/data.service';
import {Driver} from 'src/types/types';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html'
})
export class DriversComponent implements OnInit{

    drivers: Driver[] = [];
    filteredDrivers: Driver[] = [];
    age: number = 0;
    totalPages: number[] = [];
    selectedPage: number = 0;
    pageIndex: number = 0;
    errorMessage: string = "";

    constructor(private driverService: DataService, private http: HttpClient) {
    }
    ngOnInit(): void {
        this.getAllDrivers()
    }

    getAllDrivers(pageIndex?: number ) {
        if (pageIndex !== undefined) {
            this.selectedPage = pageIndex;
        }
        this.driverService.getAll<Driver>(
            "drivers",this.totalPages,this.drivers, this.errorMessage, () => {
                this.drivers.forEach((driver: Driver) => driver.age = this.driverService.age(driver.birthDate))
            },
            this.selectedPage,
            pageIndex
        )
        this.filteredDrivers = this.drivers;
    }

    deleteDriver(driver?: Driver) {
        this.driverService.delete(
          `drivers/delete/${driver?.id}`,
          driver?.firstName,
          this.getAllDrivers.bind(this)
        );
      }


}
