import { Component, OnInit } from '@angular/core';
import { ChangeTitleCommand } from '@core/commands';
import { BackupMetadataDTO } from '@core/dto';
import { BackupService, Bus, DialogService } from '@core/services';
import { Observable, tap, map } from 'rxjs';

@Component({
    selector: 'backups',
    templateUrl: './backups.component.html',
    styleUrls: ['./backups.component.scss']
})
export class BackupsComponent implements OnInit {
    backups: BackupMetadataDTO[] | null = null;
    error: any | null = null;

    constructor(private bus: Bus,
                private dialogService: DialogService,
                private backupService: BackupService) { }

    ngOnInit(): void {
        this.bus.publish(new ChangeTitleCommand("Резервное копирование"));
        this.refresh();
    }

    refresh(): void {
        let pageable = { sort: 'created,desc' };
        this.backupService.findAll(pageable).subscribe({
            next: (page) => { this.backups = page.content; },
            error: (error) => { this.error = error; }
        });
    }

    backupDatabase(): void {
        let caption = 'Внимание!';
        let question = 'Вы действительно хотите сделать резервную копию данных?';
        let backup$ = this.executeAndReload(this.backupService.backupDatabase());
        this.dialogService.confirmAndExecute(caption, question, backup$).subscribe();
    }

    restoreDatabase(event: Event): void {
        let element = event.target as HTMLInputElement;
        if (element.files && element.files.length > 0) {
            let caption = 'Внимание!';
            let question = 'Все данные будут перезаписаны. Продолжить?';
            let recovery$ = this.executeAndReload(this.backupService.restoreDatabase(element.files[0]!));
            this.dialogService.confirmAndExecute(caption, question, recovery$).subscribe();
        }
    }

    createDownloadLink(metadata: BackupMetadataDTO): Observable<string> {
        return this.backupService.createDownloadLink(metadata);
    }

    private executeAndReload<T>(action: Observable<T>): Observable<void> {
        return action.pipe(tap(() => this.refresh()), map(() => void 0));
    }
}
