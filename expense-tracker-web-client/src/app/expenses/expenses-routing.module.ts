import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExpenseEditorComponent, FilteredExpensesComponent, MonthlyExpensesComponent } from '@expenses/components';

const routes: Routes = [
    { path: 'list/monthly/:year/:month', component: MonthlyExpensesComponent },
    { path: 'list/monthly', component: MonthlyExpensesComponent },
    { path: 'list/search', component: FilteredExpensesComponent },
    { path: 'editor/:id', component: ExpenseEditorComponent },
    { path: 'editor', component: ExpenseEditorComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ExpensesRoutingModule { }
