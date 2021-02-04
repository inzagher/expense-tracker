import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { BackupMetadata } from '../model/backup-metadata';

import { MemoryDataAccessService } from './memory-data-access.service';
import { ObjectCloneService } from './object-clone.service';

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
        return this.http.get<any[]>('/api/backups').pipe(
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
            map(response => { })
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

@Injectable({ providedIn: 'root' })
export class StubBackupService extends BackupService {
    constructor(
        private memoryDataService: MemoryDataAccessService,
        private objectCloneService: ObjectCloneService,
    ) { super(); }

    list(): Observable<BackupMetadata[]> {
        return of(this.memoryDataService.backups.map(p => {
            return this.objectCloneService.deepCopy<BackupMetadata>(p);
        }));
    }

    backupDatabase(): Observable<BackupMetadata> {
        throw new Error('Method not implemented.');
    }

    restoreDatabase(backup: Blob): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
