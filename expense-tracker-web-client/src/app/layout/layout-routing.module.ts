import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '@layout/layout.component';

const CONTENT_ROUTES: Routes = [
    { path: 'backups', loadChildren: () => import('@backups/backups.module').then(m => m.BackupsModule) },
    { path: 'expenses', loadChildren: () => import('@expenses/expenses.module').then(m => m.ExpensesModule) },
    { path: 'categories', loadChildren: () => import('@categories/categories.module').then(m => m.CategoriesModule) },
    { path: 'persons', loadChildren: () => import('@persons/persons.module').then(m => m.PersonsModule) },
    { path: 'dashboard', loadChildren: () => import('@dashboard/dashboard.module').then(m => m.DashboardModule) },
    { path: '**', redirectTo: 'dashboard' }
];

const COMMON_ROUTES: Routes = [{ path: '', component: LayoutComponent, children: CONTENT_ROUTES }];

@NgModule({
    imports: [RouterModule.forChild(COMMON_ROUTES)],
    exports: [RouterModule]
})
export class LayoutRoutingModule { }
