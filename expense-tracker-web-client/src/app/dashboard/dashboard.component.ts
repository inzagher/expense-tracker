import { Component, OnInit } from '@angular/core';
import { CategoryReportItemDTO, YearlyReportItemDTO } from '@core/dto';
import { ReportService } from '@core/services';

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
    public categoryReport: CategoryReportItemDTO[] | null = null;
    public yearlyReport: YearlyReportItemDTO[] | null = null;

    constructor(private reportService: ReportService) { }

    ngOnInit(): void {
        let today = new Date();
        let year = today.getFullYear();
        let month = today.getMonth() + 1;
        this.reportService.getCategoryReport(year, month).subscribe(
            (items) => this.categoryReport = items
        );
        this.reportService.getYearlyReport(year).subscribe(
            (items) => this.yearlyReport = items
        );
    }
}
