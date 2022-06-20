import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormGroup, FormBuilder } from "@angular/forms";
import { ChangeTitleCommand } from "@core/commands";
import { CategoryDTO, ExpenseDTO, ExpenseFilterDTO, PersonDTO } from "@core/dto";
import { Bus, CategoryService, ExpenseService, PersonService } from "@core/services";
import { BehaviorSubject, catchError, finalize, map, Observable, of, switchMap, tap } from "rxjs";

@Component({
    selector: 'filtered-expenses',
    templateUrl: './filtered-expenses.component.html',
    styleUrls: ['./filtered-expenses.component.scss']
})
export class FilteredExpensesComponent implements OnInit, OnDestroy {
    private update: BehaviorSubject<void> = new BehaviorSubject<void>(void 0);

    empty$: Observable<boolean> | null = null;
    error$: Observable<any> | null = null;
    expenses$: Observable<ExpenseDTO[]> | null = null;
    persons$: Observable<PersonDTO[]> | null = null;
    categories$: Observable<CategoryDTO[]> | null = null;
    form: FormGroup = new FormGroup({});
    loading: boolean = false;

    constructor(private bus: Bus,
                private formBuilder: FormBuilder,
                private personService: PersonService,
                private categoryService: CategoryService,
                private expenseService: ExpenseService) { }

    ngOnInit(): void {
        this.form.addControl("dateFrom", this.formBuilder.control(''));
        this.form.addControl("dateTo", this.formBuilder.control(''));
        this.form.addControl("persons", this.formBuilder.control(''));
        this.form.addControl("categories", this.formBuilder.control(''));
        this.form.addControl("amountFrom", this.formBuilder.control(''));
        this.form.addControl("amountTo", this.formBuilder.control(''));
        this.form.addControl("description", this.formBuilder.control(''));
        this.bus.publish(new ChangeTitleCommand("Поиск расходов"));
        this.categories$ = this.categoryService.findAll();
        this.persons$ = this.personService.findAll();
        this.expenses$ = this.createSearchRequest();
        this.empty$ = this.expenses$.pipe(map((expenses) => expenses.length > 0));
        this.error$ = this.expenses$.pipe(catchError((error) => of(error)));
    }

    ngOnDestroy(): void {
        this.update.complete();
    }

    submit(): void {
        if (this.form.valid) {
            this.update.next(void 0);
        }
    }

    reset(): void {
        this.form.reset('');
        this.submit();
    }

    private createSearchRequest(): Observable<ExpenseDTO[]> {
        return this.update.pipe(
            tap(() => this.loading = true),
            switchMap(() => this.expenseService.findAll(this.createExpenseFilter())),
            finalize(() => this.loading = false)
        );
    }

    private createExpenseFilter(): ExpenseFilterDTO {
        return {
            dateFrom: this.form.get("dateFrom")?.value,
            dateTo: this.form.get("dateTo")?.value,
            persons: this.form.get("persons")?.value,
            categories: this.form.get("categories")?.value,
            amountFrom: this.form.get("amountFrom")?.value,
            amountTo: this.form.get("amountTo")?.value,
            description: this.form.get("description")?.value
        }
    }
}
