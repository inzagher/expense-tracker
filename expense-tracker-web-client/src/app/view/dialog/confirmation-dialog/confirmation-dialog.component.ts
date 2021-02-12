import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface IConformationDialogData {
    caption: string;
    question: string;
}

@Component({
    selector: 'app-confirmation-dialog',
    templateUrl: './confirmation-dialog.component.html',
    styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {
    constructor(
        @Inject(MAT_DIALOG_DATA) private data: IConformationDialogData,
        private dialogRef: MatDialogRef<IConformationDialogData, boolean>,
    ) { }

    public get caption(): string {
        return this.data.caption;
    }

    public get question(): string {
        return this.data.question;
    }

    confirm(): void {
        this.dialogRef.close(true);
    }

    cancel() {
        this.dialogRef.close(false);
    }
}
