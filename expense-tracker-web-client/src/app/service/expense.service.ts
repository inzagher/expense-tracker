import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Expense } from '../model/expense';

export abstract class ExpenseService {
    abstract list(): Observable<Expense[]>;
    abstract getById(id: string): Observable<Expense>;
    abstract save(expense: Expense): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpExpenseService extends ExpenseService {
    constructor(private http: HttpClient) {
        super();
    }

    list(): Observable<Expense[]> {
        return this.http.get('/api/expenses').pipe(
            map((list: any[]) => list.map(dto => this.toExpense(dto)))
        );
    }

    getById(id: string): Observable<Expense> {
        let parameters = new HttpParams().append('id', id);
        return this.http.get('/api/expenses', { params: parameters }).pipe(
            map(dto => this.toExpense(dto))
        );
    }

    save(expense: Expense): Observable<void> {
        let json = JSON.parse(JSON.stringify(expense));
        return this.http.post('/api/expenses', json).pipe(
            map(response => null)
        );
    }

    delete(id: string): Observable<void> {
        let parameters = new HttpParams().append('id', id);
        return this.http.delete('/api/expenses', { params: parameters }).pipe(
            map(response => null)
        );
    }

    private toExpense(dto: any): Expense {
        let expense = new Expense();
        expense.id = dto.id;
        expense.date = new Date(Date.parse(dto.date));
        expense.amount = dto.amount;
        expense.personId = dto.personId;
        expense.categoryId = dto.categoryId;
        expense.comment = dto.comment;
        return expense;
    }
}