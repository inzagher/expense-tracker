import { Component, OnInit } from "@angular/core";
import { UntypedFormGroup, UntypedFormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ChangeTitleCommand } from "@core/commands";
import { PersonDTO } from "@core/dto";
import { Bus, PersonService } from "@core/services";
import { of } from "rxjs";

@Component({
    selector: 'person-editor',
    templateUrl: './person-editor.component.html',
    styleUrls: ['./person-editor.component.scss']
})
export class PersonEditorComponent implements OnInit {
    id: number | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});

    constructor(private bus: Bus,
                private route: ActivatedRoute,
                private formBuilder: UntypedFormBuilder,
                private personService: PersonService) { }

    ngOnInit(): void {
        this.form.addControl("name", this.formBuilder.control('', Validators.required));

        let id = this.route.snapshot.paramMap.get('id') ?? null;
        let person$ = id ? this.personService.getById(+id) : of({ id: null, name: '' });
        person$.subscribe({ next: (p) => this.onPersonLoaded(p), error: (e) => this.onDataLoadingError(e) });

        let title = id == null ? "Новый пользователь" : "Редактирование пользователя";
        this.bus.publish(new ChangeTitleCommand(title));
    }

    submit(): void {
        if (this.form.valid) {
            let person = this.convertFormToPerson();
            let request$ = person.id == null
                ? this.personService.create(person)
                : this.personService.edit(person);
            request$.subscribe(() => window.history.back());
        }
    }

    cancel(): void {
        window.history.back();
    }

    private onPersonLoaded(person: PersonDTO): void {
        this.id = person.id;
        this.form.get("name")?.setValue(person.name);
    }

    private onDataLoadingError(error: any): void {
        console.error(error);
    }

    private convertFormToPerson(): PersonDTO {
        return {
            id: this.id,
            name: this.form.get("name")?.value
        }
    }
}
