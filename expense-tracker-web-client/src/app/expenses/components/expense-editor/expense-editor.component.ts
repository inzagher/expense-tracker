import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryDTO, PersonDTO } from '@core/dto';
import { CategoryService, PersonService } from '@core/services';

@Component({
    selector: 'expense-editor',
    templateUrl: './expense-editor.component.html',
    styleUrls: ['./expense-editor.component.scss']
})
export class ExpenseEditorComponent implements OnInit {
    form: FormGroup = new FormGroup({});
    persons: PersonDTO[] | null = null;
    categories: CategoryDTO[] | null = null;

    constructor(private formBuilder: FormBuilder,
                private personService: PersonService,
                private categoryService: CategoryService) { }

    ngOnInit(): void {
        this.form.addControl("date", this.formBuilder.control('', Validators.required));
        this.form.addControl("person", this.formBuilder.control('', Validators.required));
        this.form.addControl("category", this.formBuilder.control('', Validators.required));
        this.form.addControl("amount", this.formBuilder.control(0, Validators.required));
        this.form.addControl("description", this.formBuilder.control('', Validators.required));
        this.categoryService.findAll().subscribe(list => this.categories = list);
        this.personService.findAll().subscribe(list => this.persons = list);
    }

    submit(): void {
        if (this.persons && this.categories) {
            console.log('SUBMIT');
        }
    }

    cancel(): void {
        console.log('CANCEL');
    }
}
