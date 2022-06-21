import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { CategoryDTO, ColorDTO } from '@core/dto';
import { Bus, CategoryService } from '@core/services';
import { Observable, of } from 'rxjs';

@Component({
    selector: 'category-editor',
    templateUrl: './category-editor.component.html',
    styleUrls: ['./category-editor.component.scss']
})
export class CategoryEditorComponent implements OnInit {
    id: number | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});

    constructor(private bus: Bus,
                private route: ActivatedRoute,
                private formBuilder: UntypedFormBuilder,
                private categoryService: CategoryService) { }

    ngOnInit(): void {
        this.form.addControl("name", this.formBuilder.control('', Validators.required));
        this.form.addControl("color", this.formBuilder.control('', Validators.required));
        this.form.addControl("description", this.formBuilder.control('', Validators.required));
        this.form.addControl("obsolete", this.formBuilder.control(0, Validators.required));

        let id = this.route.snapshot.paramMap.get('id') ?? null;
        let category$ = id ? this.categoryService.getById(+id) : this.createDefaultCategory();
        category$.subscribe({ next: (c) => this.onCategoryLoaded(c), error: (e) => this.onDataLoadingError(e) });

        let title = id == null ? "Новая категория" : "Редактирование категории";
        this.bus.publish(new ChangeTitleCommand(title));
    }

    submit(): void {
        if (this.form.valid) {
            let category = this.convertFormToCategory();
            let request$ = category.id == null
                ? this.categoryService.create(category)
                : this.categoryService.edit(category);
            request$.subscribe(() => window.history.back());
        }
    }

    cancel(): void {
        window.history.back();
    }

    private onCategoryLoaded(category: CategoryDTO): void {
        this.id = category.id;
        this.form.get("name")?.setValue(category.name);
        this.form.get("color")?.setValue(this.convertColorDTOtoHex(category.color));
        this.form.get("description")?.setValue(category.description);
        this.form.get("obsolete")?.setValue(category.obsolete);
    }

    private onDataLoadingError(error: any): void {
        console.error(error);
    }

    private createDefaultCategory(): Observable<CategoryDTO> {
        return of({
            id: null,
            name: '',
            color: { red: 255, green: 255, blue: 255 },
            description: '',
            obsolete: false
        });
    }

    private convertFormToCategory(): CategoryDTO {
        return {
            id: this.id,
            name: this.form.get("name")?.value,
            color: this.convertStringToColorDTO(this.form.get("color")?.value),
            description: this.form.get("description")?.value,
            obsolete: this.form.get("obsolete")?.value
        }
    }

    private convertStringToColorDTO(value: string): ColorDTO {
        return { red: 0, green: 0, blue: 0 };
    }

    private convertColorDTOtoHex(color: ColorDTO | null): string {
        return '#000000';
    }
}
