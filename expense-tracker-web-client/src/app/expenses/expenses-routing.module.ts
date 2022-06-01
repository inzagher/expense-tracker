import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExpenseEditorComponent, ExpensesComponent, MonthlyExpensesComponent } from '@expenses/components';

const routes: Routes = [
    { path: '', component: ExpensesComponent },
    { path: 'monthly/{year}/{month}', component: MonthlyExpensesComponent },
    { path: ':id', component: ExpenseEditorComponent },
    { path: 'create', component: ExpenseEditorComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ExpensesRoutingModule { }
