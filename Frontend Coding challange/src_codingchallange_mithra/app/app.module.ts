import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { DisplayallComponent } from './components/display-all/display-all.component';
import { AddPlayerComponent } from './components/add-player/add-player.component';
import { UpdatePlayerComponent } from './components/update-player/update-player.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SearchComponent } from './components/search/search.component';
//import { SearchPlayerComponent } from './components/search-player/search-player.component';

@NgModule({
  declarations: [
    AppComponent,
    DisplayallComponent,
    AddPlayerComponent,
    UpdatePlayerComponent,
    DashboardComponent,
    SearchComponent,
    //SearchPlayerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
