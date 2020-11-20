import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BackupMetadata } from '../model/backup-metadata';

export abstract class BackupDataAccessService {
    abstract list(): Observable<BackupMetadata[]>;
    abstract backupDatabase(): Observable<BackupMetadata>;
    abstract restoreDatabase(): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpBackupDataAccessService extends BackupDataAccessService {
    list(): Observable<BackupMetadata[]> {
        throw new Error('Method not implemented.');
    }

    backupDatabase(): Observable<BackupMetadata> {
        throw new Error('Method not implemented.');
    }

    restoreDatabase(): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
