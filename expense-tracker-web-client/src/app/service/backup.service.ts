import * as uuid from 'uuid';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { BackupMetadata } from '../model/backup-metadata';
import { MemoryDataService } from './memory-data.service';
import { ObjectUtils } from '../util/object-utils';
import { RxUtils } from '../util/rx-utils';

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
    constructor(private memoryDataService: MemoryDataService) {
        super();
    }

    list(): Observable<BackupMetadata[]> {
        return RxUtils.asObservable(() =>
            this.memoryDataService.backups.map(b => {
                return ObjectUtils.deepCopy(b);
            })
        );
    }

    backupDatabase(): Observable<BackupMetadata> {
        return RxUtils.asObservable(() => {
            let metadata = new BackupMetadata();
            metadata.id = this.memoryDataService.nextBackupId();
            metadata.time = new Date();
            metadata.persons = this.memoryDataService.persons.length;
            metadata.categories = this.memoryDataService.categories.length;
            metadata.expenses = this.memoryDataService.expenses.length;
            this.memoryDataService.backups.push(metadata);
            return metadata;
        });
    }

    restoreDatabase(backup: Blob): Observable<void> {
        return RxUtils.asObservable(() => {
            this.memoryDataService.generateTestData();
        });
    }
}
