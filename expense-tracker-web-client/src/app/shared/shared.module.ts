import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '@shared/material.module';
import { ColorPipe } from '@shared/pipes';

@NgModule({
    declarations: [
        ColorPipe
    ],
    imports: [
        CommonModule,
        MaterialModule
    ],
    exports: [
        MaterialModule,
        ColorPipe
    ]
})
export class SharedModule { }
