import {Component, OnInit} from '@angular/core';
import {Ride} from "../../../types/types";
import {HttpClient} from "@angular/common/http";
import {DataService} from "../../service/data/data.service";
import {UserService} from "../../service/user/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-ride-detail',
  templateUrl: './ride-detail.component.html',
  styleUrls: ['./ride-detail.component.css']
})
export class RideDetailComponent implements OnInit {
  ride?: Ride;
  id: string | null = null;
  isEditMode: boolean = false;

  constructor(
    private http: HttpClient,
    private dataService: DataService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      this.id = params.get('id');
    });
    this.getRide()
  }

  getRide(){
    this.dataService.sendRequestData<Ride>(`rides/${this.id}`, this.http.get).subscribe({
      next: (ride)=> {
        this.ride = ride
        this.userService.getUserById(this.ride!.idUser).subscribe({
          next: (user) => {
            this.ride!.user = user
          }
        })
      }
    })
  }
}
