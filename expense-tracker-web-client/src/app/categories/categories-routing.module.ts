import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesComponent, CategoryEditorComponent } from '@categories/components';

const routes: Routes = [
    { path: '', component: CategoriesComponent },
    { path: ':id', component: CategoryEditorComponent },
    { path: 'create', component: CategoryEditorComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CategoriesRoutingModule { }
