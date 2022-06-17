import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChangeTitleCommand } from '@core/commands';
import { CategoryReportItemDTO, YearlyReportItemDTO } from '@core/dto';
import { BackupRestoredEvent } from '@core/events';
import { BusMessage, Bus, ReportService } from '@core/services';
import { MathUtils } from '@core/utils';
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

    constructor(private bus: Bus,
                private reportService: ReportService) { }

    ngOnInit(): void {
        this.subscription = this.bus.messages$.subscribe(m => this.onBusMessage(m));
        this.bus.publish(new ChangeTitleCommand("Учет расходов"));
        this.reload();
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    calculateTotalMonthyExpense(): number {
        return this.categoryReport ? MathUtils.sum(this.categoryReport, i => i.total as number) : 0;
    }

    calculateTotalYearlyExpense(): number {
        return this.yearlyReport ? MathUtils.sum(this.yearlyReport, i => i.total as number) : 0;
    }

    private onBusMessage(message: BusMessage) {
        if (message instanceof BackupRestoredEvent) {
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
