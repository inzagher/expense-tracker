import { Component, OnInit } from '@angular/core';
import { merge, Observable } from 'rxjs';
import { concatMap, map, tap } from 'rxjs/operators';

import { BackupMetadata } from 'src/app/model/backup-metadata';
import { Category } from 'src/app/model/category';
import { Person } from 'src/app/model/person';

import { AppService } from 'src/app/app.service';
import { BackupService } from 'src/app/service/backup.service';
import { CategoryService } from 'src/app/service/category.service';
import { PersonService } from 'src/app/service/person.service';

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
        return ['color', 'name', 'obsolete', 'description', 'action'];
    }

    public get pesronColumns(): string[] {
        return ['name', 'action'];
    }

    ngOnInit(): void {
        this.reload();
    }

    backupDatabase(): void {
        let caption = 'Warning';
        let question = 'Are you sure you want to backup database?';
        let backup$ = this.expecuteAndReload(this.backupService.backupDatabase());
        this.confirmAndExecute(caption, question, backup$).subscribe();
    }

    restoreDatabase(files: File[]): void {
        if (files.length > 0) {
            let caption = 'Warning';
            let question = 'All data will be overwriten. Continue?';
            let recovery$ = this.expecuteAndReload(this.backupService.restoreDatabase(files[0]));
            this.confirmAndExecute(caption, question, recovery$).subscribe();
        }
    }

    editPerson(id: string | null): void {
        this.appService.openPersonEditor(id).subscribe(
            (saved) => { if (saved) this.reload(); }
        );
    }

    editCategory(id: string | null): void {
        this.appService.openCategoryEditor(id).subscribe(
            (saved) => { if (saved) this.reload(); }
        );
    }

    deletePerson(id: string): void {
        let caption = 'Warning';
        let question = 'Are you sure you want to delete person?';
        let delete$ = this.expecuteAndReload(this.personService.delete(id));
        this.confirmAndExecute(caption, question, delete$).subscribe();
    }

    deleteCategory(id: string): void {
        let caption = 'Warning';
        let question = 'Are you sure you want to delete category?';
        let delete$ = this.expecuteAndReload(this.categoryService.delete(id));
        this.confirmAndExecute(caption, question, delete$).subscribe();
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

    private confirmAndExecute(caption: string, question: string, action: Observable<void>): Observable<void> {
        let empty = new Observable<void>(observer => observer.complete());
        return this.appService.showConfirmationDialog(caption, question).pipe(
            concatMap(confirmed => confirmed ? action : empty)
        );
    }

    private expecuteAndReload<T>(action: Observable<T>): Observable<void> {
        return action.pipe(tap(() => this.reload()), map(r => {}));
    }
}
