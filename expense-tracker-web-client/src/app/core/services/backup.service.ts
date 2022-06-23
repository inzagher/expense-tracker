import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BackupMetadataDTO, Page, PageableDTO } from "@core/dto";
import { Observable, of } from "rxjs";

@Injectable({ providedIn: 'root' })
export class BackupService {
    constructor(private http: HttpClient) {
    }

    findAll(pageable: PageableDTO): Observable<Page<BackupMetadataDTO>> {
        let url = '/api/backups';
        let params = { ...pageable }  as any;
        return this.http.get<Page<BackupMetadataDTO>>(url, { params });
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

    createDownloadLink(metadata: BackupMetadataDTO): Observable<string> {
        return of('/api/backups/download/' + metadata.id);
    }
}
