import { Component, Inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CategoryService } from './../../../service/category.service';
import { Category } from './../../../model/category';
import { ObjectUtils } from 'src/app/util/object-utils';

@Component({
    selector: 'app-category-editor-dialog',
    templateUrl: './category-editor-dialog.component.html',
    styleUrls: ['./category-editor-dialog.component.scss']
})
export class CategoryEditorDialogComponent implements OnInit {
    public model: Category | null = null;
    public error: string | null = null;

    constructor(
        @Inject(MAT_DIALOG_DATA) private id: number | null,
        private dialogRef: MatDialogRef<CategoryEditorDialogComponent, boolean>,
        private changeDetectorRef: ChangeDetectorRef,
        private categoryService: CategoryService
    ) { }

    ngOnInit(): void {
        if (this.id == null) {
            this.model = new Category();
            return;
        }

        this.categoryService.getById(this.id).subscribe(
            (category) => { this.model = category; },
            (error) => { this.error = 'Failed to load data.'; }
        );
    }

    onColorChanged(): void {
        if (this.model === null) { return; }
        this.model.color = ObjectUtils.deepCopy(this.model.color);
    }

    isValid(): boolean {
        return this.model !== null
            && this.model.name.length > 1
            && this.model.description.length > 1;
    }

    submit(): void {
        if (this.model) {
            this.error = null;
            this.categoryService.save(this.model).subscribe(
                () => { this.dialogRef.close(true); },
                (e) => { this.error = 'Failed to store data.'; }
            );
        }
    }

    cancel(): void {
        this.dialogRef.close(false);
    }
}
