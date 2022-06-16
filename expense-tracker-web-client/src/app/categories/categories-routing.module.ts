import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesComponent, CategoryEditorComponent } from '@categories/components';

const routes: Routes = [
    { path: 'list', component: CategoriesComponent },
    { path: 'editor/:id', component: CategoryEditorComponent },
    { path: 'editor', component: CategoryEditorComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CategoriesRoutingModule { }
