import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './view/page/home-page/home-page.component';
import { SettingsPageComponent } from './view/page/settings-page/settings-page.component';

const routes: Routes = [
    { path: '', component: HomePageComponent },
    { path: 'settings', component: SettingsPageComponent },
    { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
