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
  validation: userValidation = {isValid: true, error: {}};

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
                  this.user!.rideList!.push(response)
                },
                error: (err) => {
                  console.error(err);
                  Swal.fire('Error', err.message, 'error');
                },
              });
          });
        },
        error: (err) => {
          Swal.fire('Error', err.message, 'error');
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
    this.body = {
      id: user?.id,
      firstName: this.body.firstName ?
        this.validator.sanitizeInput(this.body.firstName) : user?.firstName,
      lastName: this.body.lastName ?
        this.validator.sanitizeInput(this.body.lastName) : user?.lastName,
      birthDate: this.body.birthDate ? this.body.birthDate : user?.birthDate,
      taxIdCode: this.body.taxIdCode ?
        this.validator.formatTaxIdCode(this.body.taxIdCode) : user?.taxIdCode,
      registrationDate: user?.registrationDate,
      rides: user?.rides
    };
  }

  saveUser(user?: User) {
    this.mapUserToBody(user);
    this.validation = this.validator.validateUser(this.body);

    if (!this.validation.isValid) {
      return;
    }

    this.dataService.save(
      `users/update`,
      user?.firstName,
      this.body
    );
  }

  hasError(fieldName: string): boolean {
    // @ts-ignore
    return !!(this.validation?.error && this.validation.error[fieldName]);
  }

  getErrorMessage(fieldName: string): string {
    // @ts-ignore
    return this.validation?.error?.[fieldName] || '';
  }

  isFormValid(): boolean {
    return this.validation.isValid;
  }

  onInputChange(value: string, fieldName: 'firstName' | 'lastName' | 'birthDate' | 'taxIdCode') {
    this.body = {
      ...this.body,
      [fieldName]: value
    };

    this.validateField(fieldName, value);
  }

  validateField(fieldName: 'firstName' | 'lastName' | 'birthDate' | 'taxIdCode', value: string) {
    const fieldValidation = this.validator.validateSingleField(fieldName, value, this.body);

    this.validation = {
      ...this.validation,
      error: {
        ...this.validation.error,
        [fieldName]: fieldValidation.error[fieldName]
      },
      isValid: !Object.values({
        ...this.validation.error,
        [fieldName]: fieldValidation.error[fieldName]
      }).some(error => error)
    };
  }
}
