import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from 'src/app/service/auth/auth.service';
import {LoginRequest} from 'src/types/types';

interface FormItem {
  id: string;
  label: string;
  type: string;
  input: boolean;
  icon?: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {

ngOnInit() {
    if(this.authService.isUserLoggedIn) {
        this.router.navigate(["/"])
    }
}

  errorMessage: string = '';
  loading: boolean = false;

  formItems: FormItem[] = [
    {id: "username", label: "username", type: "text", input: true, icon: "pi-user"},
    {id: "password", label: "password", type: "password", input: true, icon: "pi-lock"},
    {id: "submit", label: "login", type: "submit", input: false}
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  onSubmit() {

    if (this.loginForm.invalid) {
       return;
    }

    this.loading = true;
    this.errorMessage = '';

    const loginRequest: LoginRequest = {
      firstName: this.loginForm.value.username!,
      lastName: this.loginForm.value.password!
    };

    this.authService.login(loginRequest).subscribe({
      next: (response: any) => {
        this.loading = false;
        if (response) {
          localStorage.setItem('auth_token', response.token as string);
          localStorage.setItem('user', JSON.stringify(response.user))
          this.authService.loggedInSubject.next(true)
          this.router.navigate(['/']);
        } else {
          this.errorMessage = response.message || 'Login failed';
        }
      },
      error: (error: any) => {
        this.loading = false;
        console.error(error)
        this.errorMessage = error.message || 'An error occurred during login';
      }
    });
  }
}
