import { CommonModule } from '@angular/common';
import { MaterialModule } from '@shared/material.module';
import { HttpClientModule } from '@angular/common/http';
import { LOCALE_ID, NgModule, Optional, SkipSelf } from '@angular/core';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { CustomDateAdapter } from '@core/providers/custom-date.adapter';
import { DateFormattingUtils } from '@core/utils/date-formatting.utils';
import { ConfirmationDialogComponent } from '@core/dialogs';

@NgModule({
    declarations: [
        ConfirmationDialogComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        HttpClientModule
    ],
    providers: [
        { provide: LOCALE_ID, useValue: "en-US" },
        { provide: MAT_DATE_LOCALE, useValue: 'ru-RU' },
        { provide: MAT_DATE_FORMATS, useValue: DateFormattingUtils.getDateFormatSettings() },
        { provide: DateAdapter, useClass: CustomDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS] }
    ]
})
export class CoreModule {
    constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
        if (parentModule) {
            throw new Error('CoreModule has already been loaded.');
        }
    }
}
