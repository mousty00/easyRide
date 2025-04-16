import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import Swal from 'sweetalert2';
import { responseMessage } from '../../../types/types';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private authService: AuthService) {}

  sendRequestData<T>(
    endpoint: string,
    httpMethod: Function,
    body?: T
  ): Observable<any> {
    const url = `${this.apiUrl}/${endpoint}`;
    const options = { headers: this.authService.headers };

    if (body !== undefined) {
      return httpMethod.call(this.http, url, body, options).pipe(
        catchError((err: HttpResponse<any>) => {
          console.log('err: ', err);
          return throwError(() => new Error());
        })
      );
    } else {
      return httpMethod.call(this.http, url, options).pipe(
        catchError((err: HttpResponse<any>) => {
          console.log('err: ', err);
          return throwError(() => new Error());
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
          alert('list empty');
        }
      },
      error: (error) => {
        console.error(error);
        errorMessage = error.message;
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
            console.log(response);
            alert(response.message);
            fetchData?.();
            Swal.fire({
              title: `${name} deleted!`,
              icon: 'success',
              draggable: true,
            });
          },
          error: (err) => {
            Swal.fire('error', `${err.message}`, 'error');
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
          error: (err) => {
            Swal.fire({
              title: err.message,
              icon: 'error',
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
