import { ComponentType } from "@angular/cdk/portal";
import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ConformationDialogData, ConfirmationDialogComponent, ConfirmationResult } from "@core/dialogs";
import { Observable, filter, map, concatMap, of, take } from "rxjs";


@Injectable({ providedIn: 'root' })
export class DialogService {
    constructor(private dialog: MatDialog) {}

    askQuestion(caption: string, question: string): Observable<boolean> {
        let data: ConformationDialogData = { caption: caption, question: question  };
        return this.dialog.open(ConfirmationDialogComponent, { data: data }).afterClosed()
            .pipe(filter(r => r instanceof ConfirmationResult))
            .pipe(map(r => (r as ConfirmationResult).confirmed));
    }

    confirmAndExecute(caption: string, question: string, action$: Observable<any>): Observable<any> {
        return this.askQuestion(caption, question).pipe(
            concatMap((confirmed) => confirmed ? action$ : of(void 0))
        );
    }

    openModalDialog<T, D, R>(component: ComponentType<T>, data: D): Observable<R> {
        return this.dialog.open(component, { data: data }).afterClosed().pipe(take(1));
    }
}
