import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConformationDialogData {
    caption: string;
    question: string;
}

export class ConfirmationResult {
    public confirmed: boolean = false;

    private constructor(confirmed: boolean) {
        this.confirmed = confirmed;
    }

    static yes(): ConfirmationResult {
        return new ConfirmationResult(true);
    }

    static no(): ConfirmationResult {
        return new ConfirmationResult(false);
    }
}

@Component({
    selector: 'confirmation-dialog',
    templateUrl: './confirmation-dialog.component.html',
    styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {
    constructor(
        @Inject(MAT_DIALOG_DATA) private data: ConformationDialogData,
        private dialogRef: MatDialogRef<ConformationDialogData, ConfirmationResult>,
    ) { }

    public get caption(): string {
        return this.data.caption;
    }

    public get question(): string {
        return this.data.question;
    }

    confirm(): void {
        this.dialogRef.close(ConfirmationResult.yes());
    }

    cancel() {
        this.dialogRef.close(ConfirmationResult.no());
    }
}
