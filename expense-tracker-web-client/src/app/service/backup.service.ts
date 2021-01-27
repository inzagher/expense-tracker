import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BackupMetadata } from '../model/backup-metadata';

export abstract class BackupDataAccessService {
    abstract list(): Observable<BackupMetadata[]>;
    abstract backupDatabase(): Observable<BackupMetadata>;
    abstract restoreDatabase(): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpBackupDataAccessService extends BackupDataAccessService {
    constructor(private http: HttpClient) {
        super();
    }

    list(): Observable<BackupMetadata[]> {
        return this.http.get('/api/backups').pipe(
            map((list: any[]) => list.map(dto => this.toBackupMetadata(dto)))
        );
    }

    backupDatabase(): Observable<BackupMetadata> {
        return this.http.get('/api/backup-database').pipe(
            map((dto) => this.toBackupMetadata(dto))
        );
    }

    restoreDatabase(): Observable<void> {
        throw new Error('Method not implemented.');
    }

    private toBackupMetadata(dto: any): BackupMetadata {
        let metadata = new BackupMetadata();
        metadata.expenses = dto.expenses;
        metadata.categories = dto.categories;
        metadata.persons = dto.persons;
        metadata.id = dto.id;
        return metadata;
    }
}
