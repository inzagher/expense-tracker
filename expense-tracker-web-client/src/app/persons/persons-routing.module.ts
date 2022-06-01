import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonsComponent, PersonEditorComponent } from '@persons/components';

const routes: Routes = [
    { path: '', component: PersonsComponent },
    { path: ':id', component: PersonEditorComponent },
    { path: 'create', component: PersonEditorComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PersonsRoutingModule { }
