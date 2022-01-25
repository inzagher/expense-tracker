import { Component, OnDestroy, OnInit } from '@angular/core';
import { CategoryReportItemDTO, YearlyReportItemDTO } from '@core/dto';
import { ApplicationEvent, BackupRestoredEvent } from '@core/events';
import { EventBus, ReportService } from '@core/services';
import { Subscription } from 'rxjs';

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {
    public categoryReport: CategoryReportItemDTO[] | null = null;
    public yearlyReport: YearlyReportItemDTO[] | null = null;

    private today: Date = new Date;
    private subscription: Subscription | null = null;

    constructor(private bus: EventBus,
                private reportService: ReportService) { }

    ngOnInit(): void {
        this.subscription = this.bus.events$.subscribe(e => this.onBusEvent(e));
        this.reload();
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    private onBusEvent(event: ApplicationEvent) {
        if (event instanceof BackupRestoredEvent) {
            this.reload();
        }
    }

    private reload(): void {
        let year = this.today.getFullYear();
        let month = this.today.getMonth() + 1;
        this.reportService.getCategoryReport(year, month).subscribe(
            (items) => this.categoryReport = items
        );
        this.reportService.getYearlyReport(year).subscribe(
            (items) => this.yearlyReport = items
        );
    }
}
