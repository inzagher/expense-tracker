import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
        PersonsRoutingModule
    ]
})
export class PersonsModule { }
