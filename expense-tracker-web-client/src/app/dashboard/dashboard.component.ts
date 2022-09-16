import { formatDate } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { CategoryDTO, CategoryReportItemDTO, YearlyReportItemDTO } from '@core/dto';
import { BackupRestoredEvent } from '@core/events';
import { BusMessage, Bus, ReportService } from '@core/services';
import { DateUtils, MathUtils } from '@core/utils';
import { DateFormattingUtils } from '@material/date-formatting.utils';
import { merge, Subscription, tap, toArray } from 'rxjs';
import { Moment } from 'moment';

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    providers: [
        { provide: MAT_DATE_FORMATS, useValue: DateFormattingUtils.getYearMonthFormatSettings() }
    ]
})
export class DashboardComponent implements OnInit, OnDestroy {
    today: Date | null = null;
    subscription: Subscription | null = null;
    categoryReport: CategoryReportItemDTO[] | null = null;
    yearlyReport: YearlyReportItemDTO[] | null = null;
    period: FormControl<Moment | null> | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});
    loading: boolean = false;

    constructor(private bus: Bus,
                private router: Router,
                private route: ActivatedRoute,
                private reportService: ReportService,
                @Inject(LOCALE_ID) private locale: string) { }

    ngOnInit(): void {
        let now = new Date();
        let year = this.getIntParamFromRoute('year', now.getFullYear());
        let month = this.getIntParamFromRoute('month', now.getMonth() + 1) - 1;
        this.today = DateUtils.createUTCDate(year, month, now.getDate());
        this.period = new FormControl<Moment>(DateUtils.createUTCMoment(year, month, 1));
        this.period.addValidators(Validators.required);
        this.form.addControl('period', this.period);

        this.subscription = this.bus.messages$.subscribe(m => this.onBusMessage(m));
        this.bus.publish(new ChangeTitleCommand("Учет расходов"));
        this.reloadReportData();
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    onPeriodChanged(selected: Moment, picker: MatDatepicker<Moment>): void {
        let value = this.period?.value!;
        value.year(selected.year());
        value.month(selected.month());
        value.date(1);
        picker.close();
        this.period?.setValue(value);
        this.reloadReportData();
    }

    onCategorySelected(category: CategoryDTO | null): void {
        if (category && this.period?.valid) {
            let categories = [ category.id ];
            let selectedPeriodDate = DateUtils.toUtcDateFromMoment(this.period.value as Moment);
            let periodStart = this.toLocalDate(DateUtils.createUTCDate(selectedPeriodDate.getFullYear(), selectedPeriodDate.getMonth(), 1));
            let periodEnd = this.toLocalDate(DateUtils.createUTCDate(selectedPeriodDate.getFullYear(), selectedPeriodDate.getMonth() + 1, 0));
            let queryParams = { categories: categories, dateFrom: periodStart, dateTo: periodEnd };
            this.router.navigate(['expenses/list/search'], { queryParams: queryParams });
        }
    }

    onMonthSelected(month: number | null): void {
        if (typeof(month) === 'number' && this.period?.valid) {
            let selectedPeriodDate = DateUtils.toUtcDateFromMoment(this.period.value as Moment);
            this.router.navigate([`expenses/list/monthly/${selectedPeriodDate.getFullYear()}/${month}`]);
        }
    }

    calculateTotalMonthyExpense(): number {
        return this.categoryReport ? MathUtils.sum(this.categoryReport, i => i.total as number) : 0;
    }

    calculateTotalYearlyExpense(): number {
        return this.yearlyReport ? MathUtils.sum(this.yearlyReport, i => i.total as number) : 0;
    }

    isCurrentMonth(month: number | null): boolean {
        return !!this.today && !!month
            && month === this.today.getMonth() + 1
            && this.period?.value?.year() === this.today.getFullYear();
    }

    private onBusMessage(message: BusMessage) {
        if (message instanceof BackupRestoredEvent) {
            this.reloadReportData();
        }
    }

    private reloadReportData(): void {
        if (this.period?.valid) {
            this.loading = true;
            this.form.get('period')?.disable();
            let selectedPeriodDate = DateUtils.toUtcDateFromMoment(this.period.value as Moment);
            let year = selectedPeriodDate.getFullYear();
            let month = selectedPeriodDate.getMonth() + 1;
            let yearlyReport$ = this.reportService.getCategoryReport(year, month).pipe(
                tap((items) => this.categoryReport = items.filter(this.isCategoryReportItemVisible))
            );
            let categoryReport$ = this.reportService.getYearlyReport(year).pipe(
                tap((items) => this.yearlyReport = items)
            );
            merge(yearlyReport$, categoryReport$).pipe(toArray()).subscribe({
                next: () => { this.form.get('period')?.enable(); this.loading = false; },
                error: (e) => { console.error(e); this.loading = false; }
            });
        }
    }

    private isCategoryReportItemVisible(item: CategoryReportItemDTO): boolean {
        return item.category?.obsolete === false || item.total as number > 0;
    }

    private toLocalDate(date: Date): string {
        return formatDate(date, 'yyyy-MM-dd', this.locale);
    }

    private getIntParamFromRoute(paramName: string, orElse: number): number {
        let param  = this.route.snapshot.paramMap.get(paramName);
        return Number.parseInt(param ?? orElse.toString());
    }
}
