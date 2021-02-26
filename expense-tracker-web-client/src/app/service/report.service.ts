import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

import { Observable } from "rxjs";
import { map } from 'rxjs/operators';

import { Color } from "../model/color";
import { Category } from "../model/category";
import { CategoryReportItem } from "../model/category-report-item";
import { YearlyReportItem } from "../model/yearly-report-item";

import { MemoryDataService } from "./memory-data.service";
import { RxUtils } from "../util/rx-utils";
import { DateUtils } from "../util/date-utils";
import { ObjectUtils } from "../util/object-utils";

export abstract class ReportService {
    abstract getMonthlyCategoryReport(year: number, month: number): Observable<CategoryReportItem[]>;
    abstract getYearlyReport(year: number): Observable<YearlyReportItem[]>;
}

@Injectable({ providedIn: 'root' })
export class StubReportService extends ReportService {
    constructor(private memoryDataService: MemoryDataService) {
        super();
    }

    getMonthlyCategoryReport(year: number, month: number): Observable<CategoryReportItem[]> {
        return RxUtils.asObservable(() => {
            let report: CategoryReportItem[] = [];
            let dateFrom = new Date(year, month - 1, 1);
            let daysInMonth = DateUtils.daysInMonth(dateFrom);
            let dateTo = new Date(year, month - 1, daysInMonth);
            this.memoryDataService.categories.forEach((category) => {
                let expenses = this.memoryDataService.expenses
                    .filter(e => e.categoryId === category.id
                              && e.date.getTime() >= dateFrom.getTime()
                              && e.date.getTime() <= dateTo.getTime());
                let item = new CategoryReportItem();
                item.category = ObjectUtils.deepCopy(category);
                item.amount = expenses.reduce((sum, current) => sum + current.amount, 0);
                report.push(item);
            });
            return report;
        });
    }

    getYearlyReport(year: number): Observable<YearlyReportItem[]> {
        return RxUtils.asObservable(() => {
            let report: YearlyReportItem[] = [];
            for (let month = 1; month <= 12; ++month) {
                let dateFrom = new Date(year, month - 1, 1);
                let daysInMonth = DateUtils.daysInMonth(dateFrom);
                let dateTo = new Date(year, month - 1, daysInMonth);
                let expenses = this.memoryDataService.expenses
                    .filter(e => e.date.getTime() >= dateFrom.getTime()
                              && e.date.getTime() <= dateTo.getTime());
                let item = new YearlyReportItem();
                item.month = month;
                item.amount = expenses.reduce((sum, current) => sum + current.amount, 0);
                report.push;
            }

            return report;
        });
    }
}

@Injectable({ providedIn: 'root' })
export class HttpReportService extends ReportService {
    constructor(private http: HttpClient) {
        super();
    }

    getMonthlyCategoryReport(year: number, month: number): Observable<CategoryReportItem[]> {
        let url = `/api/reports/monthly-category-report/${year}/${month}`;
        let reportMapper = (report: any[]) => report.map(dto => this.toCategoryReportItem(dto));
        return this.http.get<any[]>(url).pipe(map(reportMapper));
    }

    getYearlyReport(year: number): Observable<YearlyReportItem[]> {
        let url = `/api/reports/yearly-report/${year}`;
        let reportMapper = (report: any[]) => report.map(dto => this.toYearlyReportItem(dto));
        return this.http.get<any[]>(url).pipe(map(reportMapper));
    }

    private toCategoryReportItem(dto: any): CategoryReportItem {
        let item = new CategoryReportItem();
        item.category = this.toCategory(dto.category);
        item.amount = dto.amount;
        return item;
    }

    private toYearlyReportItem(dto: any): YearlyReportItem {
        let item = new YearlyReportItem();
        item.month = dto.month;
        item.amount = dto.amount;
        return item;
    }

    private toCategory(dto: any): Category {
        let category = new Category();
        category.id = dto.id;
        category.name = dto.name;
        category.color = new Color();
        category.color.red = dto.color.red;
        category.color.green = dto.color.green;
        category.color.blue = dto.color.blue;
        category.description = dto.description;
        category.obsolete = dto.obsolete;
        return category;
    }
}
