import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormBuilder, UntypedFormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { CategoryDTO, ExpenseDTO, PersonDTO } from '@core/dto';
import { Bus, CalculationResult, CalculatorService, CategoryService, DictionaryService, ExpenseService, PersonService } from '@core/services';
import { concatMap, switchMap, defer, merge, Observable, of, tap, toArray, debounceTime, filter, map } from 'rxjs';
import * as moment from 'moment';

@Component({
    selector: 'expense-editor',
    templateUrl: './expense-editor.component.html',
    styleUrls: ['./expense-editor.component.scss']
})
export class ExpenseEditorComponent implements OnInit {
    id: number | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});
    persons: PersonDTO[] | null = null;
    categories: CategoryDTO[] | null = null;
    descriptions$: Observable<string[]> | null = null;
    calculation$: Observable<CalculationResult | null> | null = null;
    complete: boolean = false;
    busy: boolean = false;

    constructor(private bus: Bus,
                private location: Location,
                private route: ActivatedRoute,
                private formBuilder: UntypedFormBuilder,
                private calculatorService: CalculatorService,
                private dictionaryService: DictionaryService,
                private expenseService: ExpenseService,
                private personService: PersonService,
                private categoryService: CategoryService) { }

    ngOnInit(): void {
        let self = this;
        let calculated = function(control: AbstractControl): ValidationErrors | null {
            if (control.value === undefined || control.value === null) {
                return { required: true };
            }
            if (typeof(control.value) === 'string' && !self.calculatorService.calculate(control.value).success) {
                return { invalid: true };
            }
            return null;
        }

        this.busy = true;
        this.form.addControl("date", this.formBuilder.control('', Validators.required));
        this.form.addControl("person", this.formBuilder.control('', Validators.required));
        this.form.addControl("category", this.formBuilder.control('', Validators.required));
        this.form.addControl("amount", this.formBuilder.control(null, calculated));
        this.form.addControl("description", this.formBuilder.control('', Validators.required));

        let id = this.route.snapshot.paramMap.get('id') ?? null;
        let expense$ = id ? this.expenseService.getById(+id) : this.createDefaultExpense();
        let persons$ = this.personService.findAll().pipe(tap(list => this.persons = list));
        let categories$ = this.categoryService.findAll().pipe(tap(list => this.categories = list));
        merge(persons$, categories$).pipe(toArray(), concatMap(() => expense$)).subscribe({
            next: (expense) => this.onExpenseLoaded(expense),
            error: (error) => this.onDataLoadingError(error)
        });

        let title = id == null ? "Новый расход" : "Редактирование расхода";
        this.bus.publish(new ChangeTitleCommand(title));

        let descriptionControl = this.form.get("description");
        if (descriptionControl) {
            this.descriptions$ = descriptionControl.valueChanges.pipe(
                debounceTime(50),
                filter(value => typeof(value) === 'string'),
                switchMap((value) => this.createDescriptionSearchRequest(value))
            );
        }

        let amountControl = this.form.get("amount");
        if (amountControl) {
            let current: any = null;
            this.calculation$ = amountControl.valueChanges.pipe(
                tap(value => current = value),
                map(value => this.prepareAmount(value)),
                map(value => this.calculatorService.calculate(value)),
                map(calculation => calculation.value != current ? calculation : null)
            );
        }
    }

    submit(): void {
        if (this.form.valid) {
            let expense = this.convertFormToExpense();
            let request$ = expense.id == null
                ? this.expenseService.create(expense)
                : this.expenseService.edit(expense);
            this.busy = true;
            request$.subscribe({
                next: () => { this.location.back(); this.busy = false; this.complete = true; },
                error: (e) => { console.error(e); this.busy = false; }
            });
        }
    }

    cancel(): void {
        this.location.back();
    }

    private onExpenseLoaded(expense: ExpenseDTO): void {
        // Фильтруем категории строго после загрузки расхода,
        // т.к. устаревшие категории могут использоваться
        // в редактируемых расходах и их надо показывать.
        if (this.categories) {
            let isCategoryVisible = (c: CategoryDTO) => !c.obsolete || c.id === expense.category?.id;
            this.categories = this.categories.filter(c => isCategoryVisible(c));
        }

        this.id = expense.id;
        this.form.get("date")?.setValue(moment(expense.date));
        this.form.get("person")?.setValue(expense.person?.id);
        this.form.get("category")?.setValue(expense.category?.id);
        this.form.get("amount")?.setValue(expense.amount?.toString());
        this.form.get("description")?.setValue(expense.description);
        this.busy = false;
    }

    private onDataLoadingError(error: any): void {
        console.error(error);
        this.busy = false;
    }

    private createDescriptionSearchRequest(input: string): Observable<string[]> {
        return input.length >= 2
            ? this.dictionaryService.findDescriptions(input, 2)
            : of([]);
    }

    private createDefaultExpense(): Observable<ExpenseDTO> {
        return defer(() => {
            let date = this.route.snapshot.queryParamMap.get('date')
                ?? new Date().toISOString();
            let person: PersonDTO | null = this.persons
                ? (this.persons[0] ?? null) : null;
            let category: CategoryDTO | null = this.categories
                ? (this.categories[0] ?? null) : null;
            return of({ id: null, date: date, category: category,
                        person: person, amount: null, description: ''});
        });
    }

    private convertFormToExpense(): ExpenseDTO {
        return {
            id: this.id,
            date: this.form.get("date")?.value?.format('YYYY-MM-DD'),
            category: this.getItemById(this.categories!, this.form.get("category")?.value),
            person: this.getItemById(this.persons!, this.form.get("person")?.value),
            amount: this.calculatorService.calculate(this.form.get("amount")?.value).value,
            description: this.form.get("description")?.value?.trim()
        }
    }

    private getItemById(list: any[], id: number): any {
        let item = list.find(p => p.id === id);
        if (item) { return item; }
        else { throw Error("Элемент отсутствует в списке"); }
    }

    private prepareAmount(value: any): string | null {
        if (value === null || value === undefined) {
            return null;
        }
        if (typeof value === 'string') {
            return value.trim();
        }
        if (typeof value === 'number') {
            return value + '';
        }
        return null;
    }
}
