import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '@shared/shared.module';
import { PersonsRoutingModule } from '@persons/persons-routing.module';
import { PersonsComponent, PersonEditorComponent } from '@persons/components';

@NgModule({
    declarations: [
        PersonsComponent,
        PersonEditorComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        PersonsRoutingModule
    ]
})
export class PersonsModule { }
