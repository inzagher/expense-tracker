import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColorPipe, MonthPipe } from '@shared/pipes';
import { MaterialModule } from '@material/material.module';

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
