import { Injectable } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";

import { Observable } from "rxjs";
import { map } from "rxjs/operators";

import { CategoryEditorDialogComponent } from "./view/dialog/category-editor-dialog/category-editor-dialog.component";
import { ExpenseEditorDialogComponent } from "./view/dialog/expense-editor-dialog/expense-editor-dialog.component";
import { PersonEditorDialogComponent } from "./view/dialog/person-editor-dialog/person-editor-dialog.component";

@Injectable({ providedIn: 'root' })
export class AppService {
    constructor(private dialog: MatDialog) {
    }

    openExpenseEditor(id: string | null): Observable<boolean> {
        return this.dialog.open(ExpenseEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }

    openCategoryEditor(id: string | null): Observable<boolean> {
        return this.dialog.open(CategoryEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }

    openPersonEditor(id: string | null): Observable<boolean> {
        return this.dialog.open(PersonEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }
}
