import { CommonModule, registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '@material/material.module';
import { LOCALE_ID, NgModule, Optional, SkipSelf } from '@angular/core';
import { ConfirmationDialogComponent } from '@core/dialogs';

import localeAngularRu from '@angular/common/locales/ru';
registerLocaleData(localeAngularRu);

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
        { provide: LOCALE_ID, useValue: "ru-RU" }
    ]
})
export class CoreModule {
    constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
        if (parentModule) {
            throw new Error('CoreModule has already been loaded.');
        }
    }
}
