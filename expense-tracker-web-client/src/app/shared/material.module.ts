import { NgModule } from "@angular/core";
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatRippleModule, MatNativeDateModule } from "@angular/material/core";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatDatepickerModule  } from "@angular/material/datepicker";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatMenuModule } from "@angular/material/menu";

@NgModule({
    imports: [
        MatMenuModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatAutocompleteModule
    ],
    exports: [
        MatMenuModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatAutocompleteModule
    ]
})
export class MaterialModule { }
