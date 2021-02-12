import { Component, OnInit } from '@angular/core';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';

import { BackupMetadata } from 'src/app/model/backup-metadata';
import { Category } from 'src/app/model/category';
import { Person } from 'src/app/model/person';

import { AppService } from 'src/app/app.service';
import { BackupService } from 'src/app/service/backup.service';
import { CategoryService } from 'src/app/service/category.service';
import { PersonService as PersonService } from 'src/app/service/person.service';

@Component({
    selector: 'app-settings-page',
    templateUrl: './settings-page.component.html',
    styleUrls: ['./settings-page.component.scss']
})
export class SettingsPageComponent implements OnInit {
    public categories: Category[] | null = null;
    public persons: Person[] | null = null;
    public backups: BackupMetadata[] | null = null;
    public error: string | null = null;
    public loading: boolean = false;

    constructor(
        private appService: AppService,
        private backupService: BackupService,
        private categoryService: CategoryService,
        private personService: PersonService
    ) {  }

    public get backupColumns(): string[] {
        return ['time', 'expenses', 'categories', 'persons'];
    }

    public get categoryColumns(): string[] {
        return ['color', 'name', 'obsolete', 'description'];
    }

    public get pesronColumns(): string[] {
        return ['name'];
    }

    ngOnInit(): void {
        this.reload();
    }

    backupDatabase(): void {
        this.backupService.backupDatabase()
            .subscribe((md) => { this.reload(); });
    }

    restoreDatabase(files: File[]): void {
        if (files.length > 0) {
            this.backupService.restoreDatabase(files[0])
                .subscribe(() => { this.reload(); });
        }
    }

    editPerson(id: string | null): void {
        this.appService.openPersonEditor(id)
            .subscribe((s) => { this.reload(); });
    }

    editCategory(id: string | null): void {
        this.appService.openCategoryEditor(id)
            .subscribe((s) => { this.reload(); });
    }

    deletePerson(): void {
    }

    deleteCategory(): void {
    }

    private reload(): void {
        this.loading = true;
        this.backups = this.categories = this.persons = null;

        let backups$ = this.backupService.list().pipe(tap(list => this.backups = list));
        let categories$ = this.categoryService.list().pipe(tap(list => this.categories = list));
        let persons$ = this.personService.list().pipe(tap(list => this.persons = list));
        merge(backups$, categories$, persons$).subscribe({
            complete: () => { this.loading = false; this.error = null; },
            error: (e) => { console.log(e); }
        });
    }
}
