import { Location } from "@angular/common";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { UntypedFormGroup, UntypedFormBuilder } from "@angular/forms";
import { ActivatedRoute, Router, UrlCreationOptions, UrlSerializer } from "@angular/router";
import { Observable, debounceTime, filter, switchMap, map, tap, catchError, of, finalize, Subscription } from "rxjs";
import { ChangeTitleCommand } from "@core/commands";
import { ExpenseDTO, PersonDTO, CategoryDTO, ExpenseFilterDTO, PageableDTO } from "@core/dto";
import { Bus, PersonService, CategoryService, ExpenseService, DictionaryService } from "@core/services";
import { Moment } from 'moment';
import * as moment from 'moment';

@Component({
    selector: 'filtered-expenses',
    templateUrl: './filtered-expenses.component.html',
    styleUrls: ['./filtered-expenses.component.scss']
})
export class FilteredExpensesComponent implements OnInit, OnDestroy {
    error: any | null = null;
    loading: boolean = false;
    expenses: ExpenseDTO[] = [];
    persons$: Observable<PersonDTO[]> | null = null;
    categories$: Observable<CategoryDTO[]> | null = null;
    descriptions$: Observable<string[]> | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});
    private subscriptions: Subscription[] = [];

    constructor(private bus: Bus,
                private router: Router,
                private route: ActivatedRoute,
                private location: Location,
                private serializer: UrlSerializer,
                private formBuilder: UntypedFormBuilder,
                private personService: PersonService,
                private categoryService: CategoryService,
                private expenseService: ExpenseService,
                private dictionaryService: DictionaryService) { }

    ngOnInit(): void {
        this.applyQueryParams();
        this.categories$ = this.categoryService.findAll();
        this.persons$ = this.personService.findAll();
        this.bus.publish(new ChangeTitleCommand("Поиск расходов"));
        this.createExpenseSearchRequest().subscribe();

        let descriptionControl = this.form.get("description");
        if (descriptionControl) {
            this.descriptions$ = descriptionControl.valueChanges.pipe(
                debounceTime(50),
                filter(value => typeof(value) === 'string'),
                switchMap((value) => this.createDescriptionSearchRequest(value))
            );
        }

        this.subscriptions.push(this.form.valueChanges.subscribe(() => this.changeQueryParams()));
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe());
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

    private changeQueryParams(): void {
        let options: UrlCreationOptions = { queryParams: this.createExpenseFilter() }
        let tree = this.router.createUrlTree(['expenses/list/search'], options);
        this.location.replaceState(this.serializer.serialize(tree));
    }

    private applyQueryParams(): void {
        this.form.addControl("dateFrom", this.formBuilder.control(this.getDateFromQueryParam('dateFrom')));
        this.form.addControl("dateTo", this.formBuilder.control(this.getDateFromQueryParam('dateTo')));
        this.form.addControl("persons", this.formBuilder.control(this.getArrayFromQueryParam('persons')));
        this.form.addControl("categories", this.formBuilder.control(this.getArrayFromQueryParam('categories')));
        this.form.addControl("amountFrom", this.formBuilder.control(this.getNumberFromQueryParam('amountFrom')));
        this.form.addControl("amountTo", this.formBuilder.control(this.getNumberFromQueryParam('amountTo')));
        this.form.addControl("description", this.formBuilder.control(this.route.snapshot.queryParamMap.get('description')));
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
            criteria.dateFrom = this.form.get("dateFrom")?.value?.format('YYYY-MM-DD');
        }
        if (this.form.get("dateTo")?.value) {
            criteria.dateTo = this.form.get("dateTo")?.value?.format('YYYY-MM-DD');
        }
        if (this.form.get("persons")?.value) {
            criteria.persons = this.form.get("persons")?.value;
        }
        if (this.form.get("categories")?.value) {
            criteria.categories = this.form.get("categories")?.value;
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

    private getDateFromQueryParam(name: string): Moment | null {
        let param = this.route.snapshot.queryParamMap.get(name);
        return param === null ? null : moment(param);
    }

    private getNumberFromQueryParam(name: string): number | null {
        let param = this.route.snapshot.queryParamMap.get(name);
        return param === null ? null : Number.parseFloat(param);
    }

    private getArrayFromQueryParam(name: string): number[] {
        return this.route.snapshot.queryParamMap
            .getAll(name)
            .map(p => +p);
    }
}
