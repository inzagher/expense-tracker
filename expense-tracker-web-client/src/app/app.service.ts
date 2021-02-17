import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";

import { Observable } from "rxjs";
import { map } from "rxjs/operators";

import { ConfirmationDialogComponent, IConformationDialogData } from './view/dialog/confirmation-dialog/confirmation-dialog.component';
import { CategoryEditorDialogComponent } from "./view/dialog/category-editor-dialog/category-editor-dialog.component";
import { ExpenseEditorDialogComponent } from "./view/dialog/expense-editor-dialog/expense-editor-dialog.component";
import { PersonEditorDialogComponent } from "./view/dialog/person-editor-dialog/person-editor-dialog.component";

@Injectable({ providedIn: 'root' })
export class AppService {
    constructor(private dialog: MatDialog) {
    }

    showConfirmationDialog(caption: string, question: string): Observable<boolean> {
        let data: IConformationDialogData = { caption: caption, question: question  };
        return this.dialog.open(ConfirmationDialogComponent, { data: data })
            .afterClosed().pipe(map(r => !!r));
    }

    openExpenseEditor(id: number | null): Observable<boolean> {
        return this.dialog.open(ExpenseEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }

    openCategoryEditor(id: number | null): Observable<boolean> {
        return this.dialog.open(CategoryEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }

    openPersonEditor(id: number | null): Observable<boolean> {
        return this.dialog.open(PersonEditorDialogComponent, { data: id })
            .afterClosed().pipe(map(r => !!r));
    }
}
