import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExpenseEditorComponent, ExpensesComponent, MonthlyExpensesComponent } from '@expenses/components';

const routes: Routes = [
    { path: 'list/search', component: ExpensesComponent },
    { path: 'list/monthly/:year/:month', component: MonthlyExpensesComponent },
    { path: 'list/monthly', component: MonthlyExpensesComponent },
    { path: 'editor/:id', component: ExpenseEditorComponent },
    { path: 'editor', component: ExpenseEditorComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ExpensesRoutingModule { }
