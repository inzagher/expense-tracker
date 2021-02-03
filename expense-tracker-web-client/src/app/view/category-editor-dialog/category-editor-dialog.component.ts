import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CategoryService } from './../../service/category.service';
import { Category } from './../../model/category';

@Component({
    selector: 'app-category-editor-dialog',
    templateUrl: './category-editor-dialog.component.html',
    styleUrls: ['./category-editor-dialog.component.scss']
})
export class CategoryEditorDialogComponent implements OnInit {
    public model: Category = null;
    public error: string = null;

    constructor(
        @Inject(MAT_DIALOG_DATA) private id: string | null,
        private dialogRef: MatDialogRef<CategoryEditorDialogComponent, boolean>,
        private categoryService: CategoryService
    ) { }

    ngOnInit(): void {
        if (this.id == null) {
            this.model = new Category();
        }

        this.categoryService.getById(this.id).subscribe(
            (category) => { this.model = category; },
            (error) => { this.error = 'Failed to load data.'; }
        );
    }

    isValid(): boolean {
        return false;
    }

    submit(): void {
        this.dialogRef.close(true);
    }

    cancel(): void {
        this.dialogRef.close(false);
    }
}
