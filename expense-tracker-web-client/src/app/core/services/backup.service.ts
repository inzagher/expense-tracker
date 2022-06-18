import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BackupMetadataDTO } from "@core/dto";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class BackupService {
    constructor(private http: HttpClient) {
    }

    findAll(): Observable<BackupMetadataDTO[]> {
        let url = '/api/backups';
        return this.http.get<BackupMetadataDTO[]>(url);
    }

    backupDatabase(): Observable<BackupMetadataDTO> {
        let url = '/api/backups/create';
        return this.http.post<BackupMetadataDTO>(url, {});
    }

    restoreDatabase(backup: Blob): Observable<void> {
        let url = '/api/backups/restore';
        let formData = new FormData();
        formData.append('file', backup);
        return this.http.post<void>(url, formData);
    }
}
