import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BackupMetadata } from '../model/backup-metadata';

export abstract class BackupService {
    abstract list(): Observable<BackupMetadata[]>;
    abstract backupDatabase(): Observable<BackupMetadata>;
    abstract restoreDatabase(backup: Blob): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpBackupService extends BackupService {
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

    restoreDatabase(backup: Blob): Observable<void> {
        let formData = new FormData();
        formData.append('file', backup);
        return this.http.post('/api/restore-database', formData).pipe(
            map(response => null)
        );
    }

    private toBackupMetadata(dto: any): BackupMetadata {
        let metadata = new BackupMetadata();
        metadata.id = dto.id;
        metadata.time = new Date(Date.parse(dto.time));
        metadata.expenses = dto.expenses;
        metadata.categories = dto.categories;
        metadata.persons = dto.persons;
        return metadata;
    }
}
