import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {NotFoundPageComponent} from "./pages/not-found-page/not-found-page.component";
import {ProfileComponent} from './pages/profile/profile.component';
import {RidesComponent} from './pages/rides/rides.component';
import {UsersComponent} from './pages/users/users.component';
import {DriversComponent} from './pages/drivers/drivers/drivers.component';
import {VehiclesComponent} from './pages/vehicles/vehicles/vehicles.component';
import {UserDetailComponent} from './pages/users/user-detail/user-detail.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "login", component: LoginComponent},
  {path: "home", redirectTo: "", pathMatch: "full"},
  {path: "profile", component: ProfileComponent },
  {path: "rides", component: RidesComponent },
  {path: "users", component: UsersComponent },
  {path: "users/:id", component:  UserDetailComponent},
  {path: "drivers", component: DriversComponent },
  {path: "vehicles", component: VehiclesComponent },
  {path: "**", component: NotFoundPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
