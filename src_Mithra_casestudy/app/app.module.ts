import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { RegisterComponent } from './component/register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
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
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component'

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    RegisterComponent,
    LoginComponent,
    ShowCarsComponent,
    ReserveComponent,
    PaymentComponent,
    SuccessComponent,
    HistoryComponent,
    ChangeDateComponent,
    AboutComponent,
    ContactComponent,
    ProfileComponent,
    AddCarComponent,
    UpdateCarComponent,
    ViewUsersComponent,
    ReportComponent,
    CheckComponent,
    HomeComponent,
    ForgotPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
