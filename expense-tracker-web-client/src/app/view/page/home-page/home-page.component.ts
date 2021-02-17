import { Component, OnInit } from '@angular/core';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';

import { AppService } from 'src/app/app.service';
import { CategoryService } from 'src/app/service/category.service';
import { ExpenseService } from 'src/app/service/expense.service';
import { PersonService } from 'src/app/service/person.service';
import { DateUtils } from 'src/app/util/date-utils';

import { Expense } from 'src/app/model/expense';
import { Category } from 'src/app/model/category';
import { Person } from 'src/app/model/person';
import { ExpenseFilter } from 'src/app/model/expense-filter';

@Component({
    selector: 'app-home-page',
    templateUrl: './home-page.component.html',
    styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
    public date: Date = new Date();
    public expenses: Expense[] | null = null;
    public categories: Category[] | null = null;
    public persons: Person[] | null = null;
    public error: string | null = null;
    public loading: boolean = false;

    constructor(
        private appService: AppService,
        private expenseService: ExpenseService,
        private categoryService: CategoryService,
        private personService: PersonService
    ) { }

    ngOnInit(): void {
        this.reload();
    }

    reload(): void {
        this.loading = true;
        this.expenses = this.categories = this.persons = null;

        let month = this.date.getMonth();
        let year = this.date.getFullYear();
        let daysInMonth = DateUtils.daysInMonth(this.date);

        let expenseFilter = new ExpenseFilter();
        expenseFilter.from = new Date(year, month, 1);
        expenseFilter.to = new Date(year, month, daysInMonth);

        let persons$ = this.personService.list().pipe(tap(list => this.persons = list));
        let categories$ = this.categoryService.list().pipe(tap(list => this.categories = list));
        let expenses$ = this.expenseService.find(expenseFilter).pipe(tap(list => this.expenses = list));
        merge(persons$, categories$, expenses$).subscribe({
            complete: () => { this.loading = false; this.error = null; },
            error: (e) => { console.log(e); }
        });
    }

    editExpense(id: number | null) {
        this.appService.openExpenseEditor(id)
            .subscribe((s) => { this.reload(); });
    }

    deleteExpense(id: number) {
    }
}
