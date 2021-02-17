import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { PersonService } from './../../../service/person.service';
import { Person } from './../../../model/person';

@Component({
   selector: 'app-person-editor-dialog',
   templateUrl: './person-editor-dialog.component.html',
   styleUrls: ['./person-editor-dialog.component.scss']
})
export class PersonEditorDialogComponent implements OnInit {
    public model: Person | null = null;
    public error: string | null = null;

    constructor(
        @Inject(MAT_DIALOG_DATA) private id: number | null,
        private dialogRef: MatDialogRef<PersonEditorDialogComponent, boolean>,
        private personService: PersonService
    ) { }

    ngOnInit(): void {
        if (this.id == null) {
            this.model = new Person();
            return;
        }

        this.personService.getById(this.id).subscribe(
            (person) => { this.model = person; },
            (error) => { this.error = 'Failed to load data.'; }
        );
    }

    isValid(): boolean {
        return !!this.model && !!this.model.name && this.model.name.length > 0;
    }

    submit(): void {
        if (this.model) {
            this.error = null;
            this.personService.save(this.model).subscribe(
                () => { this.dialogRef.close(true); },
                (e) => { this.error = 'Failed to store data.'; }
            );
        }
    }

    cancel(): void {
        this.dialogRef.close(false);
    }
}
