import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {NotFoundPageComponent} from './pages/not-found-page/not-found-page.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ProfileComponent} from './pages/profile/profile.component';
import {RidesComponent} from './pages/rides/rides.component';
import {UsersComponent} from './pages/users/users.component';
import {CardComponent} from './components/cards/card/card.component';
import {DriversComponent} from './pages/drivers/drivers/drivers.component';
import {VehiclesComponent} from './pages/vehicles/vehicles/vehicles.component';
import {PaginationComponent} from './components/buttons/pagination/pagination.component';
import {SearchBarComponent} from './components/search-bar/search-bar.component';
import {EditCardComponent} from './components/cards/edit-card/edit-card.component';
import {UserDetailComponent} from './pages/users/user-detail/user-detail.component';
import {CustomInputComponent} from './components/custom-input/custom-input.component';
import {CustomButtonComponent} from './components/buttons/custom-button/custom-button.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToggleButtonComponent} from './components/buttons/toggle-button/toggle-button.component';
import {NgOptimizedImage} from "@angular/common";
import { RideCardComponent } from './components/cards/ride-card/ride-card.component';
import { FilterButtonComponent } from './components/buttons/filter-button/filter-button.component';
import { RideDetailComponent } from './pages/ride-detail/ride-detail.component';

@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    NavbarComponent,
    AppComponent,
    NotFoundPageComponent,
    ProfileComponent,
    RidesComponent,
    UsersComponent,
    CardComponent,
    DriversComponent,
    VehiclesComponent,
    PaginationComponent,
    SearchBarComponent,
    EditCardComponent,
    UserDetailComponent,
    CustomInputComponent,
    CustomButtonComponent,
    ToggleButtonComponent,
    RideCardComponent,
    FilterButtonComponent,
    RideDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    NgOptimizedImage
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
