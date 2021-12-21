import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CategoryReportItemDTO, YearlyReportItemDTO } from "@core/dto";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class ReportService {
    constructor(private http: HttpClient) {
    }

    getCategoryReport(year: number, month: number): Observable<CategoryReportItemDTO[]> {
        let url = `/api/reports/category/${year}/${month}`;
        return this.http.get<CategoryReportItemDTO[]>(url);
    }

    getYearlyReport(year: number): Observable<YearlyReportItemDTO[]> {
        let url = `/api/reports/yearly/${year}`;
        return this.http.get<YearlyReportItemDTO[]>(url);
    }
}
