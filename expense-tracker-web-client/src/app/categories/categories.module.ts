import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "@shared/shared.module";
import { CategoriesRoutingModule } from "./categories-routing.module";
import { CategoryEditorComponent, CategoriesComponent } from "./components";


@NgModule({
    declarations: [
        CategoryEditorComponent,
        CategoriesComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        CategoriesRoutingModule
    ]
})
export class CategoriesModule { }
