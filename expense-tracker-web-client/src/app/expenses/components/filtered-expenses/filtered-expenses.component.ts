import { formatDate } from "@angular/common";
import { Component, OnInit, Inject, LOCALE_ID } from "@angular/core";
import { UntypedFormGroup, UntypedFormBuilder } from "@angular/forms";
import { Router } from "@angular/router";
import { ChangeTitleCommand } from "@core/commands";
import { ExpenseDTO, PersonDTO, CategoryDTO, ExpenseFilterDTO, PageableDTO } from "@core/dto";
import { Bus, PersonService, CategoryService, ExpenseService, DictionaryService } from "@core/services";
import { DateUtils } from "@core/utils";
import { Observable, debounceTime, filter, switchMap, map, tap, catchError, of, finalize } from "rxjs";


@Component({
    selector: 'filtered-expenses',
    templateUrl: './filtered-expenses.component.html',
    styleUrls: ['./filtered-expenses.component.scss']
})
export class FilteredExpensesComponent implements OnInit {
    error: any | null = null;
    loading: boolean = false;
    expenses: ExpenseDTO[] = [];
    persons$: Observable<PersonDTO[]> | null = null;
    categories$: Observable<CategoryDTO[]> | null = null;
    descriptions$: Observable<string[]> | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});

    constructor(private bus: Bus,
                private router: Router,
                private formBuilder: UntypedFormBuilder,
                private personService: PersonService,
                private categoryService: CategoryService,
                private expenseService: ExpenseService,
                private dictionaryService: DictionaryService,
                @Inject(LOCALE_ID) private locale: string) { }

    ngOnInit(): void {
        this.form.addControl("dateFrom", this.formBuilder.control(null));
        this.form.addControl("dateTo", this.formBuilder.control(null));
        this.form.addControl("persons", this.formBuilder.control(''));
        this.form.addControl("categories", this.formBuilder.control(''));
        this.form.addControl("amountFrom", this.formBuilder.control(''));
        this.form.addControl("amountTo", this.formBuilder.control(''));
        this.form.addControl("description", this.formBuilder.control(''));
        this.bus.publish(new ChangeTitleCommand("Поиск расходов"));
        this.categories$ = this.categoryService.findAll();
        this.persons$ = this.personService.findAll();
        this.createExpenseSearchRequest().subscribe();

        let descriptionControl = this.form.get("description");
        if (descriptionControl) {
            this.descriptions$ = descriptionControl.valueChanges.pipe(
                debounceTime(50),
                filter(value => typeof(value) === 'string'),
                switchMap((value) => this.createDescriptionSearchRequest(value))
            );
        }
    }

    submit(): void {
        if (this.form.valid) {
            this.createExpenseSearchRequest().subscribe();
        }
    }

    reset(): void {
        this.form.reset('');
        this.submit();
    }

    editExpense(expense: ExpenseDTO): void {
        this.router.navigate(['expenses/editor/' + expense.id]);
    }

    private createExpenseSearchRequest(): Observable<ExpenseDTO[]> {
        this.loading = true;
        let criteria = this.createExpenseFilter();
        let pageable = this.createPagingParams();
        return this.expenseService.findAll(criteria, pageable).pipe(
            map((page) => page.content),
            tap((expenses) => { this.expenses = expenses; }),
            catchError((error) => { this.error = error; return of([]); }),
            finalize(() => this.loading = false)
        );
    }

    private createDescriptionSearchRequest(input: string): Observable<string[]> {
        return input.length >= 2
            ? this.dictionaryService.findDescriptions(input, 2)
            : of([]);
    }

    private createExpenseFilter(): ExpenseFilterDTO {
        let criteria: ExpenseFilterDTO = {};
        if (this.form.get("dateFrom")?.value) {
            criteria.dateFrom = this.toLocalDate(this.form.get("dateFrom")?.value);
        }
        if (this.form.get("dateTo")?.value) {
            criteria.dateTo = this.toLocalDate(this.form.get("dateTo")?.value);
        }
        if (this.form.get("persons")?.value) {
            criteria.person = this.form.get("persons")?.value;
        }
        if (this.form.get("categories")?.value) {
            criteria.category = this.form.get("categories")?.value;
        }
        if (this.form.get("amountFrom")?.value) {
            criteria.amountFrom = this.form.get("amountFrom")?.value;
        }
        if (this.form.get("amountTo")?.value) {
            criteria.amountTo = this.form.get("amountTo")?.value;
        }
        if (this.form.get("description")?.value) {
            criteria.description = this.form.get("description")?.value;
        }
        return criteria;
    }

    private createPagingParams(): PageableDTO {
        return { page: 0, size: 50, sort: 'date,desc' };
    }

    private toLocalDate(input: string | null): string | undefined {
        if (input === null) { return undefined; }
        return formatDate(DateUtils.parseUTCDate(input), 'YYYY-MM-dd', this.locale);
    }
}
