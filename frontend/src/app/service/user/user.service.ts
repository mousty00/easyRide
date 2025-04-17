import { Injectable } from '@angular/core';
import { DataService } from '../data/data.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private dataService: DataService,private http: HttpClient ) { }

  getUserById<bodyUserRequest>(id: number): Observable<any> {
    return this.dataService.sendRequestData<bodyUserRequest>(`users/${id}`, this.http.get)
  }

}
