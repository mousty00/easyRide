import {Component, OnInit} from '@angular/core';
import {DataService} from 'src/app/service/data/data.service';
import {bodyUserRequest, User, userValidation} from 'src/types/types';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {UserValidatorService} from 'src/app/service/validator/user-validator.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
})
export class UserDetailComponent implements OnInit {
  user: User | null = null;
  id: string | null = '';
  isEditMode: boolean = false;
  body: bodyUserRequest = {};
  validation: userValidation | any = {};
  validationErrors: string[] = [];

  constructor(
    private userService: DataService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private dataService: DataService,
    private validator: UserValidatorService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.id = params.get('id');
    });
    this.getUserById();
  }

  getUserById(): void {
    this.userService
      .sendRequestData(`users/${this.id}`, this.http.get)
      .subscribe({
        next: (response) => {
          this.user = response;
          if (!this.user?.rideList) {
            this.user!.rideList = [];
          }
          this.user!.age = this.userService.age(this.user!.birthDate!)
          this.user?.rides!.forEach((id) => {
            this.userService
              .sendRequestData(`rides/${id}`, this.http.get)
              .subscribe({
                next: (response) => {
                  console.log(response)
                  this.user!.rideList!.push(response)
                },
                error: (err) => {
                  console.error(err);
                  alert(err.message);
                },
              });
          });
        },
        error: (err) => {
          Swal.fire(
            'error',
            `${err.message}`,
            'error'
          )
        },
      });
  }

  deleteUser(user?: User) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.dataService.delete(
        `users/delete/${user?.id}`,
        user?.firstName
      );
    }
  }

  mapUserToBody(user?: User) {
    const sanitizedFirstName = this.body.firstName ?
      this.validator.sanitizeInput(this.body.firstName) : user?.firstName;

    const sanitizedLastName = this.body.lastName ?
      this.validator.sanitizeInput(this.body.lastName) : user?.lastName;

    const sanitizedTaxIdCode = this.body.taxIdCode ?
      this.validator.formatTaxIdCode(this.body.taxIdCode) : user?.taxIdCode;

    this.body = {
      id: user?.id,
      firstName: sanitizedFirstName,
      lastName: sanitizedLastName,
      birthDate: this.body.birthDate ? this.body.birthDate : user?.birthDate,
      taxIdCode: sanitizedTaxIdCode,
      registrationDate: user?.registrationDate,
      rides: user?.rides
    }
  }

  saveUser(user?: User) {
    this.mapUserToBody(user);

    this.validation = this.validator.validateUser(this.body);

    if (!this.validation.isValid ) {
      this.validationErrors = this.validation.errors;
      return;
    }

    this.validationErrors = [];

    this.dataService.save(
      `users/update`,
      user?.firstName,
      this.body
    );
  }

  onInputChange($event: string | Date, value: string) {
    // @ts-ignore
    this.body[value] = $event
  }

  hasError(fieldName: string): boolean {
     if(this.validation?.error?.[fieldName] == null) {
        return false;
     }
     return true;
  }

}
