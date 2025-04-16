import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import Swal from 'sweetalert2';
import { responseMessage } from '../../../types/types';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  apiUrl = environment.apiUrl;
  isOptionsRequest: boolean = false;

  constructor(private http: HttpClient, private authService: AuthService) {}

  sendRequestData<T>(
    endpoint: string,
    httpMethod: Function,
    body?: T
  ): Observable<any> {
    const url = `${this.apiUrl}/${endpoint}`;
    const options = { headers: this.authService.headers };
    this.isOptionsRequest = httpMethod === this.http.options;

    if (body !== undefined) {
      return httpMethod.call(this.http, url, body, options).pipe(
        catchError((error: HttpErrorResponse) => {
          return Swal.fire({
            title: error.error.message,
            icon: 'error',
            draggable: true,
          });
        })
      );
    } else {
      return httpMethod.call(this.http, url, options).pipe(
        catchError((error: HttpErrorResponse) => {
          return Swal.fire({
            title: error.status === 0 ? 'server is down' : error.error.message,
            icon: 'error',
            draggable: true,
          });
        })
      );
    }
  }

  getAll<T>(
    endpoint: string,
    totalPages: number[],
    dataList: T[],
    errorMessage: string,
    getAge?: () => void,
    selectedPage?: number,
    pageIndex?: number
  ) {
    this.sendRequestData<T[]>(
      `${endpoint}?page=${pageIndex ? pageIndex : 0}`,
      this.http.get
    ).subscribe({
      next: (response) => {
        if (totalPages.length == 0) {
          for (let i = 0; i < response.totalPages; i++) {
            totalPages.push(i);
          }
        }
        selectedPage = pageIndex;
        if (response.results) {
          dataList.length = 0;
          response.results.forEach((data: T) => dataList.push(data));
          if (getAge) getAge();
        } else {
            Swal.fire({
                title: 'list empty',
                icon: 'info',
                draggable: true,
              });
        }
      },
    });
  }

  delete<T>(
    endpoint: string,
    name: string | undefined,
    fetchData?: () => void
  ) {
    Swal.fire({
      title: `Do you want to delete ${name}?`,
      showDenyButton: true,
      showCancelButton: true,
      denyButtonText: `No`,
      confirmButtonText: 'Delete',
    }).then((result) => {
      if (result.isConfirmed) {
        this.sendRequestData(`${endpoint}`, this.http.delete).subscribe({
          next: (response) => {
            fetchData?.();
            Swal.fire({
              title: `${name} deleted!`,
              icon: 'success',
              draggable: true,
            });
          },
        });
      } else if (result.isDenied) {
        Swal.fire(`${name} not deleted`, '', 'info');
      }
    });
  }

  save<T>(
    endpoint: string,
    name?: string | undefined,
    body?: any,
    fetchData?: () => void
  ) {
    Swal.fire({
      title: `Do you want to save ${name}?`,
      showDenyButton: true,
      showCancelButton: true,
      denyButtonText: `No`,
      confirmButtonText: 'Save',
    }).then((result) => {
      if (result.isConfirmed) {
        this.sendRequestData(`${endpoint}`, this.http.put, body).subscribe({
          next: (response: responseMessage) => {
            fetchData?.();
            Swal.fire({
              title: response.message,
              icon: 'success',
              draggable: true,
            });
          },
        });
      } else if (result.isDenied) {
        Swal.fire(`${name} not saved`, '', 'info');
      }
    });
  }

  age(dateString: string): number {
    const date = new Date(
      dateString.replace(/(\d{2})-(\d{2})-(\d{4})/, '$2/$1/$3')
    );
    const currentYear = new Date().getFullYear();
    return currentYear - date.getFullYear();
  }
}
