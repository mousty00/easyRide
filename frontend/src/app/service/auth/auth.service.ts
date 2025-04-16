import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequest} from 'src/types/types';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public loggedInSubject = new BehaviorSubject<boolean>(this.isUserLoggedIn);
  public loggedIn$ = this.loggedInSubject.asObservable();
  private tokenSubject = new BehaviorSubject<string>(this.token!);
  public token$ = this.tokenSubject.asObservable();
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  get isUserLoggedIn(): boolean {
    return localStorage.getItem('auth_token') != null;
  }

  get token(): string | null {
    return localStorage.getItem('auth_token');
  }

  get headers(): HttpHeaders | undefined {
    return new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.token}`
      });
  }

  login(credentials: LoginRequest): Observable<Response> {
    return this.http.post<Response>(`${this.baseUrl}/users/login`, credentials);
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    this.loggedInSubject.next(false);
  }

  verifyToken(token: string): Observable<Response> {
      return this.http.post<Response>(`${this.baseUrl}/users/verify`, token);
    }
}
