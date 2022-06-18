import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChangeTitleCommand } from '@core/commands';
import { BackupMetadataDTO } from '@core/dto';
import { BackupService, Bus, DialogService } from '@core/services';
import { Observable, tap, map, switchMap, BehaviorSubject } from 'rxjs';

@Component({
    selector: 'backups',
    templateUrl: './backups.component.html',
    styleUrls: ['./backups.component.scss']
})
export class BackupsComponent implements OnInit, OnDestroy {
    public backups$: Observable<BackupMetadataDTO[]> | null = null;
    private update: BehaviorSubject<void> = new BehaviorSubject<void>(void 0);

    constructor(private bus: Bus,
                private dialogService: DialogService,
                private backupService: BackupService) { }

    public get backupColumns(): string[] {
        return ['time', 'expenses', 'categories', 'persons'];
    }

    ngOnInit(): void {
        this.backups$ = this.update.pipe(switchMap(() => this.backupService.findAll()));
        this.bus.publish(new ChangeTitleCommand("Резервное копирование"));
    }

    ngOnDestroy(): void {
        this.update.complete();
    }

    refresh(): void {
        this.update.next();
    }

    backupDatabase(): void {
        let caption = 'Внимание!';
        let question = 'Вы действительно хотите сделать резервную копию данных?';
        let backup$ = this.expecuteAndReload(this.backupService.backupDatabase());
        this.dialogService.confirmAndExecute(caption, question, backup$).subscribe();
    }

    restoreDatabase(event: Event): void {
        let element = event.target as HTMLInputElement;
        if (element.files && element.files.length > 0) {
            let caption = 'Внимание!';
            let question = 'Все данные будут перезаписаны. Продолжить?';
            let recovery$ = this.expecuteAndReload(this.backupService.restoreDatabase(element.files[0]!));
            this.dialogService.confirmAndExecute(caption, question, recovery$).subscribe();
        }
    }

    private expecuteAndReload<T>(action: Observable<T>): Observable<void> {
        return action.pipe(tap(() => this.refresh()), map(() => void 0));
    }
}
