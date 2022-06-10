import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoryDTO, ExpenseDTO, PersonDTO } from '@core/dto';
import { CategoryService, DictionaryService, ExpenseService, PersonService } from '@core/services';
import { concatMap, switchMap, defer, merge, Observable, of, tap, toArray, debounceTime, filter } from 'rxjs';

@Component({
    selector: 'expense-editor',
    templateUrl: './expense-editor.component.html',
    styleUrls: ['./expense-editor.component.scss']
})
export class ExpenseEditorComponent implements OnInit {
    id: number | null = null;
    form: FormGroup = new FormGroup({});
    persons: PersonDTO[] | null = null;
    categories: CategoryDTO[] | null = null;
    descriptions$: Observable<string[]> | null = null;

    constructor(private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private dictionaryService: DictionaryService,
                private expenseService: ExpenseService,
                private personService: PersonService,
                private categoryService: CategoryService) { }

    ngOnInit(): void {
        this.form.addControl("date", this.formBuilder.control('', Validators.required));
        this.form.addControl("person", this.formBuilder.control('', Validators.required));
        this.form.addControl("category", this.formBuilder.control('', Validators.required));
        this.form.addControl("amount", this.formBuilder.control(0, Validators.required));
        this.form.addControl("description", this.formBuilder.control('', Validators.required));

        let id = this.route.snapshot.paramMap.get('id') ?? null;
        let expense$ = id ? this.expenseService.getById(+id) : this.createDefaultExpense();
        let persons$ = this.personService.findAll().pipe(tap(list => this.persons = list));
        let categories$ = this.categoryService.findAll().pipe(tap(list => this.categories = list));
        merge(persons$, categories$).pipe(toArray(), concatMap(() => expense$)).subscribe({
            next: (expense) => this.onExpenseLoaded(expense),
            error: (error) => this.onDataLoadingError(error)
        });

        let descriptionControl = this.form.get("description");
        if (descriptionControl) {
            this.descriptions$ = descriptionControl.valueChanges.pipe(
                debounceTime(100),
                filter(value => typeof(value) === 'string'),
                filter(value => (value as string).length > 2),
                switchMap((value) => this.dictionaryService.findDescriptions(value, 2))
            );
        }
    }

    submit(): void {
        if (this.form.valid) {
            let expense = this.convertFormToExpense();
            let request$ = expense.id == null
                ? this.expenseService.create(expense)
                : this.expenseService.edit(expense);
            request$.subscribe(() => window.history.back());
        }
    }

    cancel(): void {
        window.history.back();
    }

    private onExpenseLoaded(expense: ExpenseDTO): void {
        this.id = expense.id;
        this.form.get("date")?.setValue(expense.date);
        this.form.get("person")?.setValue(expense.person?.id);
        this.form.get("category")?.setValue(expense.category?.id);
        this.form.get("amount")?.setValue(expense.amount);
        this.form.get("description")?.setValue(expense.description);
    }

    private onDataLoadingError(error: any): void {
        console.error(error);
    }

    private createDefaultExpense(): Observable<ExpenseDTO> {
        return defer(() => {
            let date = new Date().toISOString();
            let person: PersonDTO | null = this.persons
                ? (this.persons[0] ?? null) : null;
            let category: CategoryDTO | null = this.categories
                ? (this.categories[0] ?? null) : null;
            return of({ id: null, date: date, category: category,
                        person: person, amount: 0, description: ''});
        });
    }

    private convertFormToExpense(): ExpenseDTO {
        return {
            id: this.id,
            date: this.form.get("date")?.value,
            category: this.getItemById(this.categories!, this.form.get("category")?.value),
            person: this.getItemById(this.persons!, this.form.get("person")?.value),
            amount: this.form.get("amount")?.value,
            description: this.form.get("description")?.value
        }
    }

    private getItemById(list: any[], id: number): any {
        let item = list.find(p => p.id === id);
        if (item) { return item; }
        else { throw Error("Элемент отсутствует в списке"); }
    }
}
