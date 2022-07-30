import { formatDate, Location, ViewportScroller } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { ExpenseDTO } from '@core/dto';
import { Bus, DialogService, ExpenseService } from '@core/services';
import { AnimationUtils, DateUtils, MathUtils } from '@core/utils';
import { DateFormattingUtils } from '@material/date-formatting.utils';
import { map, tap } from 'rxjs';
import { Moment } from 'moment';

@Component({
    selector: 'monthly-expenses',
    templateUrl: './monthly-expenses.component.html',
    styleUrls: ['./monthly-expenses.component.scss'],
    animations: [ AnimationUtils.getRowExpansionTrigger() ],
    providers: [
        { provide: MAT_DATE_FORMATS, useValue: DateFormattingUtils.getYearMonthFormatSettings() }
    ]
})
export class MonthlyExpensesComponent implements OnInit {
    loading: boolean = false;
    report: DailyReportItem[] = [];
    period: FormControl<Moment | null> | null = null;
    form: UntypedFormGroup = new UntypedFormGroup({});
    selectedReportItem: DailyReportItem | null = null;
    initialized: boolean = false;

    constructor(private bus: Bus,
                private router: Router,
                private location: Location,
                private route: ActivatedRoute,
                private scroller: ViewportScroller,
                private dialogService: DialogService,
                private expenseService: ExpenseService,
                @Inject(LOCALE_ID) private locale: string) { }

    ngOnInit(): void {
        let today = new Date();
        let year = this.getIntParamFromRoute('year', today.getFullYear());
        let month = this.getIntParamFromRoute('month', today.getMonth() + 1) - 1;
        this.period = new FormControl<Moment>(DateUtils.createUTCMoment(year, month, 1));
        this.period.addValidators(Validators.required);
        this.form.addControl('period', this.period);
        this.bus.publish(new ChangeTitleCommand("Расходы за месяц"));
        this.reloadExpenseList();
    }

    onMonthChanged(selected: Moment, picker: MatDatepicker<Moment>): void {
        let value = this.period?.value!;
        value.year(selected.year());
        value.month(selected.month());
        value.date(1);
        picker.close();
        let pathParams = `${selected.year()}/${selected.month() + 1 }`;
        this.location.replaceState('/expenses/list/monthly/' + pathParams);
        this.period?.setValue(value);
        this.reloadExpenseList();
    }

    onReportItemClick(item: DailyReportItem): void {
        let path = window.location.pathname;
        this.selectedReportItem = item === this.selectedReportItem ? null : item;
        let search = this.selectedReportItem === null ? '' : '?selectedDate=' + this.toLocalDate(item.date);
        this.location.replaceState(path + search);
    }

    isDayOff(date: Date | null): boolean {
        return date?.getDay() === 6
            || date?.getDay() === 0;
    }

    addExpense(date: Date | null): void {
        let extras: NavigationExtras = {};
        if (date) { extras.queryParams = { date: this.toLocalDate(date) } }
        this.router.navigate(['expenses/editor'], extras);
    }

    editExpense(expense: ExpenseDTO): void {
        this.router.navigate(['expenses/editor/' + expense.id]);
    }

    deleteExpense(expense: ExpenseDTO): void {
        let caption = 'Внимание!';
        let question = 'Расход будет удален безвозвратно. Продолжить?';
        let deletion$ = this.expenseService.deleteById(expense.id!).pipe(
            tap(() => this.reloadExpenseList()),
            map(() => void 0)
        );
        this.dialogService.confirmAndExecute(caption, question, deletion$).subscribe();
    }

    getItemDetailsState(item: DailyReportItem): string {
        return item === this.selectedReportItem ? 'expanded' : 'collapsed';
    }

    calculateTotalMonthlyExpense(report: DailyReportItem[]): number {
        return MathUtils.sum(report, i => this.calculateTotalDaylyExpense(i.expenses));
    }

    calculateTotalDaylyExpense(expenses: ExpenseDTO[]): number {
        return MathUtils.sum(expenses, e => e.amount ?? 0);
    }

    private reloadExpenseList(): void {
        if (this.period?.valid) {
            this.loading = true;
            this.form.get('period')?.disable();
            let date = DateUtils.toUtcDateFromMoment(this.period.value as Moment);
            let start = this.toLocalDate(DateUtils.createUTCDate(date.getFullYear(), date.getMonth(), 1));
            let end = this.toLocalDate(DateUtils.createUTCDate  (date.getFullYear(), date.getMonth() + 1, 0));
            this.expenseService.findAll({ dateFrom: start, dateTo: end }, { size: 1000 }).subscribe({
                next: (page) => { this.applyLoadedExpenses(page.content); },
                error: (error) => { this.handleLoadingError(error); }
            });
        }
    }

    private applyLoadedExpenses(expenses: ExpenseDTO[]): void {
        let value = this.period?.value;
        this.form.get('period')?.enable();

        let selectedDate = DateUtils.toUtcDateFromMoment(value as Moment);
        if (this.isAnotherMonthReport(this.report, selectedDate)) {
            this.report = [];
        }

        let currentDay = 1;
        let currentDate = DateUtils.createUTCDate(selectedDate.getFullYear(), selectedDate.getMonth(), 1);
        while (selectedDate.getMonth() == currentDate.getMonth()) {
            let dailyExpenses = expenses.filter(e => this.isSameDayExpense(e, currentDate));
            let totalAmount = MathUtils.sum(dailyExpenses, e => e.amount as number);
            let currentDateReportItems = this.report.filter(ri => this.areEqual(ri.date, currentDate));
            if (currentDateReportItems.length > 0) {
                let item = currentDateReportItems[0] as DailyReportItem;
                item.expenses = dailyExpenses;
                item.totalAmount = totalAmount;
            } else {
                this.report.push(new DailyReportItem(currentDate, dailyExpenses, totalAmount));
            }
            currentDate = DateUtils.createUTCDate(currentDate.getFullYear(), currentDate.getMonth(), ++currentDay);
        }

        if (this.initialized === false) {
            this.restoreDateSelection();
        }

        this.loading = false;
        this.initialized = true;
    }

    private handleLoadingError(error: any) {
        this.form.get('period')?.enable();
        console.error(error);
        this.loading = false;
    }

    private restoreDateSelection(): void {
        let param = this.route.snapshot.queryParamMap.get('selectedDate');
        let selectedDate = param === null ? null : DateUtils.parseUTCDate(param);
        this.selectedReportItem = this.report.find(item => DateUtils.areEqual(item.date, selectedDate)) ?? null;
        if (selectedDate && selectedDate.getDate() > 2) {
            let year = selectedDate.getFullYear();
            let month = selectedDate.getMonth();
            let date = selectedDate.getDate() - 2;
            let scrollDate = DateUtils.createUTCDate(year, month, date);
            this.scrollToReportItem(scrollDate);
        }
    }

    private scrollToReportItem(scrollDate: Date): void {
        setTimeout(() => {
            let anchor = "report-item-" + scrollDate.getDate();
                this.scroller.scrollToAnchor(anchor);
        }, 1);
    }

    private getIntParamFromRoute(paramName: string, orElse: number): number {
        let param  = this.route.snapshot.paramMap.get(paramName);
        return Number.parseInt(param ?? orElse.toString());
    }

    private areEqual(left: Date, right: Date): boolean {
        return left.getTime() === right.getTime();
    }

    private parseDateFromString(input: string): Date {
        return new Date(Date.parse(input));
    }

    private isSameDayExpense(expense: ExpenseDTO, date: Date): boolean {
        return !!expense.date && this.areEqual(this.parseDateFromString(expense.date), date);
    }

    private isAnotherMonthReport(report: DailyReportItem[], date: Date): boolean {
        return report.some(dri => dri.date.getFullYear() !== date.getFullYear()
                               || dri.date.getMonth() !== date.getMonth());
    }

    private toLocalDate(date: Date): string {
        return formatDate(date, 'YYYY-MM-dd', this.locale);
    }
}

class DailyReportItem {
    constructor(public date: Date,
                public expenses: ExpenseDTO[],
                public totalAmount: number) { }
}
