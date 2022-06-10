import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class DictionaryService {
    constructor(private http: HttpClient) {
    }

    findDescriptions(pattern: string, minCount: number): Observable<string[]> {
        let url = '/api/expenses/descriptions';
        let params: any = { pattern, minCount };
        return this.http.get<string[]>(url, { params: params });
    }
}
