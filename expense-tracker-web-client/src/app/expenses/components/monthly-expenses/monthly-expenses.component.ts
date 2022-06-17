import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { ExpenseDTO } from '@core/dto';
import { Bus, ExpenseService } from '@core/services';
import { MathUtils } from '@core/utils';

@Component({
    selector: 'monthly-expenses',
    templateUrl: './monthly-expenses.component.html',
    styleUrls: ['./monthly-expenses.component.scss']
})
export class MonthlyExpensesComponent implements OnInit {
    report: DailyReportItem[] | null = null;
    month: number | null = null;
    year: number | null = null;

    constructor(private bus: Bus,
                private route: ActivatedRoute,
                private expenseService: ExpenseService) { }

    ngOnInit(): void {
        let date = new Date();
        this.year = this.getIntParamFromRoute('year', date.getFullYear());
        this.month = this.getIntParamFromRoute('year', date.getMonth() + 1);
        this.bus.publish(new ChangeTitleCommand("Расходы за месяц"));
        this.reloadExpenseList();
    }

    private reloadExpenseList(): void {
        if (this.year == null || this.month == null) {
            return;
        }

        let start = this.createUTCDate(this.year, this.month - 1, 1).toISOString();
        let end = this.createUTCDate(this.year, this.month, 0).toISOString();
        this.expenseService.findAll({ dateFrom: start, dateTo: end }).subscribe({
            next: (list) => { this.applyLoadedExpenses(list); },
            error: (error) => { console.error(error); }
        });
    }

    private applyLoadedExpenses(expenses: ExpenseDTO[]): void {
        if (this.year == null || this.month == null) {
            return;
        }

        if (this.report == null || this.isSameMonthReport(this.report, this.year, this.month)) {
            this.report = [];
        }

        let day = 1;
        let date = this.createUTCDate(this.year, this.month - 1, day);

        while (date.getMonth() == this.month - 1) {
            let dailyExpenses = expenses.filter(e => this.isSameDayExpense(e, date));
            let totalAmount = MathUtils.sum(dailyExpenses, e => e.amount as number);
            let currentDateReportItems = this.report.filter(ri => this.areEqual(ri.date, date));
            if (currentDateReportItems.length > 0) {
                let item = currentDateReportItems[0] as DailyReportItem;
                item.expenses = dailyExpenses;
                item.totalAmount = totalAmount;
            } else {
                this.report.push(new DailyReportItem(date, dailyExpenses, totalAmount));
            }
            date = this.createUTCDate(this.year, this.month - 1, ++day);
        }
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

    private createUTCDate(y: number, m: number, d: number): Date {
        return new Date(Date.UTC(y, m, d, 0, 0, 0));
    }

    private isSameDayExpense(expense: ExpenseDTO, date: Date): boolean {
        return !!expense.date && this.areEqual(this.parseDateFromString(expense.date), date);
    }

    private isSameMonthReport(report: DailyReportItem[], year: number, month: number): boolean {
        return report.some(dri => dri.date.getMonth() === (month - 1)
                               && dri.date.getFullYear() == year);
    }
}

class DailyReportItem {
    constructor(public date: Date,
                public expenses: ExpenseDTO[],
                public totalAmount: number) { }
}
