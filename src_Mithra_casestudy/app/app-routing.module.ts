import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './component/register/register.component';
import { LoginComponent } from './component/login/login.component';
import { ShowCarsComponent } from './component/show-cars/show-cars.component';
import { ReserveComponent } from './component/reserve/reserve.component';
import { PaymentComponent } from './component/payment/payment.component';
import { SuccessComponent } from './component/success/success.component';
import { HistoryComponent } from './component/history/history.component';

import { ChangeDateComponent } from './component/change-date/change-date.component';
import { AboutComponent } from './component/about/about.component';
import { ContactComponent } from './component/contact/contact.component';
import { ProfileComponent } from './component/profile/profile.component';
import { AddCarComponent } from './component/add-car/add-car.component';
import { UpdateCarComponent } from './component/update-car/update-car.component';
import { ViewUsersComponent } from './component/view-users/view-users.component';
import { ReportComponent } from './component/report/report.component';
import { CheckComponent } from './component/check/check.component';
import { HomeComponent } from './component/home/home.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';

const routes: Routes = [
  {path:'register',component:RegisterComponent},
  {path:'login',component:LoginComponent},
  {path:'showCars',component:ShowCarsComponent},
  {path:'reserve',component:ReserveComponent},
  {path:'pay',component:PaymentComponent},
  {path:'success',component:SuccessComponent},
  {path:'history',component:HistoryComponent},
  {path:'changeDate',component:ChangeDateComponent},
  {path:'about',component:AboutComponent},
  {path:'contact',component:ContactComponent},
  {path:'profile',component:ProfileComponent},
  {path:'addCar',component:AddCarComponent},
  {path:'updateCar',component:UpdateCarComponent},
  {path:'viewUsers',component:ViewUsersComponent},
  {path:'report',component:ReportComponent},
  {path:'check',component:CheckComponent},
  { path: 'home', component: HomeComponent },
{ path: '', redirectTo: 'home', pathMatch: 'full' },
 { path: 'forgot-password', component: ForgotPasswordComponent },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
