import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Expense } from '../model/expense';

export abstract class ExpenseDataAccessService {
    abstract list(): Observable<Expense[]>;
    abstract getById(id: string): Observable<Expense>;
    abstract save(expense: Expense): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpExpenseDataAccessService extends ExpenseDataAccessService {
    list(): Observable<Expense[]> {
        return of([]);
    }
    getById(id: string): Observable<Expense> {
        throw new Error('Method not implemented.');
    }
    save(expense: Expense): Observable<void> {
        throw new Error('Method not implemented.');
    }
    delete(id: string): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
