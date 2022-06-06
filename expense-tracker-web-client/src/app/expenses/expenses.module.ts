import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExpensesRoutingModule } from '@expenses/expenses-routing.module';
import { ExpensesComponent, MonthlyExpensesComponent, ExpenseEditorComponent } from '@expenses/components';

@NgModule({
    declarations: [
        ExpensesComponent,
        MonthlyExpensesComponent,
        ExpenseEditorComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        ExpensesRoutingModule
    ]
})
export class ExpensesModule { }
