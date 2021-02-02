import { Component, OnInit } from '@angular/core';
import { merge } from 'rxjs';
import { tap } from 'rxjs/operators';

import { BackupMetadata } from 'src/app/model/backup-metadata';
import { Category } from 'src/app/model/category';
import { Person } from 'src/app/model/person';

import { BackupService } from 'src/app/service/backup.service';
import { CategoryService } from 'src/app/service/category.service';
import { PersonDataAccessService } from 'src/app/service/person.service';

@Component({
    selector: 'app-settings-page',
    templateUrl: './settings-page.component.html',
    styleUrls: ['./settings-page.component.scss']
})
export class SettingsPageComponent implements OnInit {
    public categories: Category[] = null;
    public persons: Person[] = null;
    public backups: BackupMetadata[] = null;
    public loading: boolean = false;
    public error: string = null;

    constructor(
        private backupService: BackupService,
        private categoryService: CategoryService,
        private personService: PersonDataAccessService
    ) {  }

    ngOnInit(): void {
        this.reload();
    }

    public get columns(): string[] {
        return ['time', 'expenses', 'categories', 'persons']
    }

    private reload() {
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
