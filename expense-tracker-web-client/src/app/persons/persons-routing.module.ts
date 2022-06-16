import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PersonsComponent, PersonEditorComponent } from '@persons/components';

const routes: Routes = [
    { path: 'list', component: PersonsComponent },
    { path: 'editor/:id', component: PersonEditorComponent },
    { path: 'editor', component: PersonEditorComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PersonsRoutingModule { }
