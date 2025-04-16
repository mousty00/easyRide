import { Injectable } from '@angular/core';
import { User, bodyUserRequest, userValidation, userError } from 'src/types/types';

@Injectable({
  providedIn: 'root'
})
export class UserValidatorService {
  constructor() { }

  validateUser(user: User | bodyUserRequest): userValidation {
    const error: userError = {};
    let isValid = true;

    if (!user.firstName || user.firstName.trim() === '') {
      error.firstName = 'First name is required';
      isValid = false;
    } else if (user.firstName.length > 50) {
      error.firstName = 'First name cannot exceed 50 characters';
      isValid = false;
    }

    if (!user.lastName || user.lastName.trim() === '') {
      error.lastName = 'Last name is required';
      isValid = false;
    } else if (user.lastName.length > 50) {
      error.lastName = 'Last name cannot exceed 50 characters';
      isValid = false;
    }

    if (!user.birthDate) {
      error.birthDate = 'Birth date is required';
      isValid = false;
    } else {
      const birthDate = new Date(user.birthDate);
      const today = new Date();
      const age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();

      if (birthDate > today) {
        error.birthDate = 'Birth date cannot be in the future';
        isValid = false;
      } else if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        if (age - 1 < 18) {
          error.birthDate = 'User must be at least 18 years old';
          isValid = false;
        }
      } else if (age < 18) {
        error.birthDate = 'User must be at least 18 years old';
        isValid = false;
      }
    }

    if (!user.taxIdCode || user.taxIdCode.trim() === '') {
      error.taxIdCode = 'Tax ID Code is required';
      isValid = false;
    } else if (user.taxIdCode.length < 9 || user.taxIdCode.length > 16) {
      error.taxIdCode = 'Tax ID Code must be between 9 and 16 characters';
      isValid = false;
    }

    return {
      isValid: isValid,
      error: error
    };
  }

  validateSingleField (
    fieldName: 'firstName' | 'lastName' | 'birthDate' | 'taxIdCode',
    value: string,
    user: User | bodyUserRequest
  ): userValidation {
    const error: userError = {};
    let isValid = true;

    switch (fieldName) {
      case 'firstName':
        if (!value || value.trim() === '') {
          error.firstName = 'First name is required';
          isValid = false;
        } else if (value.length > 50) {
          error.firstName = 'First name cannot exceed 50 characters';
          isValid = false;
        }
        break;

      case 'lastName':
        if (!value || value.trim() === '') {
          error.lastName = 'Last name is required';
          isValid = false;
        } else if (value.length > 50) {
          error.lastName = 'Last name cannot exceed 50 characters';
          isValid = false;
        }
        break;

      case 'birthDate':
        if (!value) {
          error.birthDate = 'Birth date is required';
          isValid = false;
        } else {
          const birthDate = new Date(value);
          const today = new Date();
          const age = today.getFullYear() - birthDate.getFullYear();
          const monthDiff = today.getMonth() - birthDate.getMonth();

          if (birthDate > today) {
            error.birthDate = 'Birth date cannot be in the future';
            isValid = false;
          } else if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            if (age - 1 < 18) {
              error.birthDate = 'User must be at least 18 years old';
              isValid = false;
            }
          } else if (age < 18) {
            error.birthDate = 'User must be at least 18 years old';
            isValid = false;
          }
        }
        break;

      case 'taxIdCode':
        if (!value || value.trim() === '') {
          error.taxIdCode = 'Tax ID Code is required';
          isValid = false;
        } else if (value.length < 9 || value.length > 16) {
          error.taxIdCode = 'Tax ID Code must be between 9 and 16 characters';
          isValid = false;
        }
        break;
    }

    return {
      isValid: isValid,
      error: error
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
