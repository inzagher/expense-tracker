import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExpensesRoutingModule } from './expenses-routing.module';
import { ExpensesComponent, MonthlyExpensesComponent, ExpenseEditorComponent } from '@expenses/components';


@NgModule({
    declarations: [
        ExpensesComponent,
        MonthlyExpensesComponent,
        ExpenseEditorComponent
    ],
    imports: [
        CommonModule,
        ExpensesRoutingModule
    ]
})
export class ExpensesModule { }
