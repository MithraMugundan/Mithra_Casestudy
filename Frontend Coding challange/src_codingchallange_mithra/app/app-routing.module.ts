import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddPlayerComponent } from './components/add-player/add-player.component';
import { UpdatePlayerComponent } from './components/update-player/update-player.component';
import { DisplayallComponent } from './components/display-all/display-all.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SearchComponent } from './components/search/search.component';
//import { SearchPlayerComponent } from './components/search-player/search-player.component';

const routes: Routes = [
  { path: 'home', component: DashboardComponent },
  { path: 'addplayer', component: AddPlayerComponent },
  { path: 'updateplayer/:id', component: UpdatePlayerComponent },
  { path: 'displayall', component: DisplayallComponent },
  { path: 'search/:input', component: SearchComponent },
 // { path: 'search/:input', component: SearchPlayerComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }, // default redirect
  { path: '**', redirectTo: 'home' } // wildcard fallback
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
