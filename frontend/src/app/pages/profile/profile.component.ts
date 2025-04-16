import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/service/auth/auth.service';
import {User} from 'src/types/types';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
router = inject(Router)
route = inject(ActivatedRoute)
authService = inject(AuthService)
user: User;

constructor() {
    this.user = JSON.parse(localStorage.getItem("user")!)
}

ngOnInit(){
    if(localStorage.getItem("auth_token") === null) {
        this.router.navigate(["/login"])
    }
}

logout() {
    this.authService.logout()
    this.router.navigate(["/"])
}

}
