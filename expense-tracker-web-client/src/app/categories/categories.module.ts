import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { CategoriesRoutingModule } from '@categories/categories-routing.module';
import { CategoryEditorComponent, CategoriesComponent } from '@categories/components';

@NgModule({
    declarations: [
        CategoryEditorComponent,
        CategoriesComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        CategoriesRoutingModule
    ]
})
export class CategoriesModule { }
