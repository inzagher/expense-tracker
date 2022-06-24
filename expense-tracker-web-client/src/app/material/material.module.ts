import { NgModule } from "@angular/core";
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatRippleModule, MatNativeDateModule, DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from "@angular/material/core";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatDatepickerModule  } from "@angular/material/datepicker";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatMenuModule } from "@angular/material/menu";
import { MatDialogModule } from "@angular/material/dialog";
import { MatTableModule } from "@angular/material/table";
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS } from "@angular/material-moment-adapter";
import { CustomDateAdapter } from "@material/custom-date.adapter";
import { DateFormattingUtils } from "@material/date-formatting.utils";

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
        MatAutocompleteModule,
        MatDialogModule,
        MatTableModule
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
        MatAutocompleteModule,
        MatDialogModule,
        MatTableModule
    ],
    providers: [
        { provide: MAT_DATE_LOCALE, useValue: 'ru-RU' },
        { provide: MAT_DATE_FORMATS, useValue: DateFormattingUtils.getDateFormatSettings() },
        { provide: DateAdapter, useClass: CustomDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS] }
    ]
})
export class MaterialModule { }
