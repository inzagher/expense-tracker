import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '@shared/material.module';
import { ColorPipe, MonthPipe } from '@shared/pipes';

@NgModule({
    declarations: [
        ColorPipe,
        MonthPipe
    ],
    imports: [
        CommonModule,
        MaterialModule
    ],
    exports: [
        MaterialModule,
        ColorPipe,
        MonthPipe
    ]
})
export class SharedModule { }
