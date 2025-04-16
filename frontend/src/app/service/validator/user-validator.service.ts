import { Injectable } from '@angular/core';
import {User, bodyUserRequest, userValidation, userError} from 'src/types/types';

@Injectable({
  providedIn: 'root'
})
export class UserValidatorService {

  error: userError = {};
  isValid: boolean = true;

  constructor() { }

  validateUser(user: User | bodyUserRequest): userValidation {

    if (!user.firstName || user.firstName === '' || user.firstName.trim() === '') {
      this.error.firstName = 'First name is required';
      this.isValid = false;
    }

    if (!user.lastName || user.lastName.trim() === '') {
      this.error.lastName ='Last name is required';
      this.isValid = false;
    }

    if (!user.birthDate) {
      this.error.lastName = 'Birth date is required';
      this.isValid = false;
    }

    if (!user.taxIdCode || user.taxIdCode.trim() === '') {
      this.error.taxIdCode = 'Tax ID Code is required';
      this.isValid = false;
    }

    if (user.firstName && user.firstName.length > 50) {
      this.error.lastName = 'First name cannot exceed 50 characters';
      this.isValid = false;
    }

    if (user.lastName && user.lastName.length > 50) {
      this.error.lastName = 'Last name cannot exceed 50 characters';
      this.isValid = false;
    }

    if (user.taxIdCode && (user.taxIdCode.length < 9 || user.taxIdCode.length > 16)) {
      this.error.taxIdCode = 'Tax ID Code must be between 9 and 16 characters';
      this.isValid = false;
    }

    if (user.birthDate) {
      const birthDate = new Date(user.birthDate);
      const today = new Date();
      const age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();

      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        if (age - 1 < 18) {
          this.error.birthDate = 'User must be at least 18 years old';
          this.isValid = false;
        }
      } else {
        if (age < 18) {
          this.error.birthDate = 'User must be at least 18 years old';
          this.isValid = false;
        }
      }

      if (birthDate > today) {
        this.error.birthDate = 'Birth date cannot be in the future';
        this.isValid = false;
      }
    }


    return {
      isValid: this.isValid,
      error: this.error
    };
  }

  formatTaxIdCode(taxId: string): string {
    return taxId.toUpperCase().replace(/\s/g, '');
  }

  sanitizeInput(input: string): string {
    if (!input) return '';
    return input
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
  }
}
