import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SettingsPageComponent } from './view/page/settings-page/settings-page.component';

const routes: Routes = [
    { path: 'settings', component: SettingsPageComponent },
    { path: '**', redirectTo: 'settings' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
