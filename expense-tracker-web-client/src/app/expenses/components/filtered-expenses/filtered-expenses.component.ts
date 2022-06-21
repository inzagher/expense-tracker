import { formatDate } from "@angular/common";
import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
import { FormGroup, FormBuilder } from "@angular/forms";
import { ChangeTitleCommand } from "@core/commands";
import { CategoryDTO, ExpenseDTO, ExpenseFilterDTO, PageableDTO, PersonDTO } from "@core/dto";
import { Bus, CategoryService, ExpenseService, PersonService } from "@core/services";
import { DateUtils } from "@core/utils";
import { catchError, finalize, map, Observable, of, tap } from "rxjs";

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
    form: FormGroup = new FormGroup({});

    constructor(private bus: Bus,
                private formBuilder: FormBuilder,
                private personService: PersonService,
                private categoryService: CategoryService,
                private expenseService: ExpenseService,
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
        this.createSearchRequest().subscribe();
    }

    submit(): void {
        if (this.form.valid) {
            this.createSearchRequest().subscribe();
        }
    }

    reset(): void {
        this.form.reset('');
        this.submit();
    }

    private createSearchRequest(): Observable<ExpenseDTO[]> {
        this.loading = true;
        let filter = this.createExpenseFilter();
        let pageable = this.createPagingParams();
        return this.expenseService.findAll(filter, pageable).pipe(
            map((page) => page.content),
            tap((expenses) => { this.expenses = expenses; }),
            catchError((error) => { this.error = error; return of([]); }),
            finalize(() => this.loading = false)
        );
    }

    private createExpenseFilter(): ExpenseFilterDTO {
        let filter: ExpenseFilterDTO = {};
        if (this.form.get("dateFrom")?.value) {
            filter.dateFrom = this.toLocalDate(this.form.get("dateFrom")?.value);
        }
        if (this.form.get("dateTo")?.value) {
            filter.dateTo = this.toLocalDate(this.form.get("dateTo")?.value);
        }
        if (this.form.get("description")?.value) {
            filter.description = this.form.get("description")?.value;
        }
        return filter;
    }

    private createPagingParams(): PageableDTO {
        return { page: 0, size: 50, sort: 'date,desc' };
    }

    private toLocalDate(input: string | null): string | undefined {
        if (input === null) { return undefined; }
        return formatDate(DateUtils.parseUTCDate(input), 'YYYY-MM-dd', this.locale);
    }
}
