import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '@material/material.module';
import { LOCALE_ID, NgModule, Optional, SkipSelf } from '@angular/core';
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
        { provide: LOCALE_ID, useValue: "en-US" }
    ]
})
export class CoreModule {
    constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
        if (parentModule) {
            throw new Error('CoreModule has already been loaded.');
        }
    }
}
