import { formatDate } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { CategoryDTO, CategoryReportItemDTO, YearlyReportItemDTO } from '@core/dto';
import { BackupRestoredEvent } from '@core/events';
import { BusMessage, Bus, ReportService } from '@core/services';
import { DateUtils, MathUtils } from '@core/utils';
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
                private router: Router,
                private reportService: ReportService,
                @Inject(LOCALE_ID) private locale: string) { }

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

    onCategorySelected(category: CategoryDTO | null): void {
        if (category) {
            let categories = [ category.id ];
            let periodStart = this.toLocalDate(DateUtils.createUTCDate(this.today.getFullYear(), this.today.getMonth(), 1));
            let periodEnd = this.toLocalDate(DateUtils.createUTCDate(this.today.getFullYear(), this.today.getMonth() + 1, 0));
            let queryParams = { categories: categories, dateFrom: periodStart, dateTo: periodEnd };
            this.router.navigate(['expenses/list/search'], { queryParams: queryParams });
        }
    }

    onMonthSelected(month: number | null): void {
        if (typeof(month) === 'number') {
            let year = this.today.getFullYear();
            this.router.navigate([`expenses/list/monthly/${year}/${month}`]);
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
            (items) => this.categoryReport = items.filter(this.isCategoryReportItemVisible)
        );
        this.reportService.getYearlyReport(year).subscribe(
            (items) => this.yearlyReport = items
        );
    }

    private isCategoryReportItemVisible(item: CategoryReportItemDTO): boolean {
        return item.category?.obsolete === false || item.total as number > 0;
    }

    private toLocalDate(date: Date): string {
        return formatDate(date, 'yyyy-MM-dd', this.locale);
    }
}
