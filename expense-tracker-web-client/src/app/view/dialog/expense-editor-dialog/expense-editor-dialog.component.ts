import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { Observable, iif, merge } from 'rxjs';
import { tap } from 'rxjs/operators';

import { Person } from 'src/app/model/person';
import { Category } from 'src/app/model/category';
import { Expense } from 'src/app/model/expense';

import { CategoryService } from 'src/app/service/category.service';
import { ExpenseService } from 'src/app/service/expense.service';
import { PersonService } from 'src/app/service/person.service';
import { RxUtils } from 'src/app/util/rx-utils';

@Component({
    selector: 'app-expense-editor-dialog',
    templateUrl: './expense-editor-dialog.component.html',
    styleUrls: ['./expense-editor-dialog.component.scss']
})
export class ExpenseEditorDialogComponent implements OnInit {
    public categories: Category[] | null = null;
    public persons: Person[] | null = null;
    public model: Expense | null = null;
    public error: string | null = null;

    constructor(
        @Inject(MAT_DIALOG_DATA) private id: number | null,
        private dialogRef: MatDialogRef<ExpenseEditorDialogComponent, boolean>,
        private expenseService: ExpenseService,
        private categoryService: CategoryService,
        private personService: PersonService
    ) { }

    ngOnInit(): void {
        let persons$ = this.personService.list().pipe(tap(list => this.persons = list));
        let categories$ = this.categoryService.list().pipe(tap(list => this.categories = list));
        let expense$ = this.loadExpense().pipe(tap(expense => this.model = expense));
        merge(persons$, categories$, expense$).subscribe({
            error: (error) => { this.error = 'Failed to load data.'; }
        });
    }

    isValid(): boolean {
        return false;
    }

    submit(): void {
        if (this.model) {
            this.error = null;
            this.expenseService.save(this.model).subscribe(
                () => { this.dialogRef.close(true); },
                (e) => { this.error = 'Failed to store data.'; }
            );
        }
    }

    cancel(): void {
        this.dialogRef.close(false);
    }

    private loadExpense(): Observable<Expense> {
        return iif(() => this.id !== null,
            this.expenseService.getById(this.id as number),
            RxUtils.asObservable(() => new Expense())
        );
    }
}
