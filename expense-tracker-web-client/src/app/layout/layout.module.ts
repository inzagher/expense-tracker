import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { LayoutRoutingModule } from '@layout/layout-routing.module';
import { LayoutComponent } from '@layout/layout.component';

@NgModule({
    declarations: [LayoutComponent],
    imports: [
        CommonModule,
        SharedModule,
        LayoutRoutingModule,
    ]
})
export class LayoutModule { }
