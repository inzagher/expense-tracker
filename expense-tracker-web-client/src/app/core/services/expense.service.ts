import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ExpenseDTO } from "@core/dto";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class ExpenseService {
    constructor(private http: HttpClient) {
    }

    findAll(): Observable<ExpenseDTO[]> {
        let url = `/api/expenses`;
        return this.http.get<ExpenseDTO[]>(url);
    }

    getById(id: number): Observable<ExpenseDTO> {
        let url = `/api/expenses/${id}`;
        return this.http.get<ExpenseDTO>(url);
    }

    create(expense: ExpenseDTO): Observable<void> {
        let url = '/api/expenses';
        return this.http.post<void>(url, expense);
    }

    edit(expense: ExpenseDTO): Observable<void> {
        let url = `/api/expenses/${expense.id}`;
        return this.http.put<void>(url, expense);
    }

    deleteById(id: number): Observable<void> {
        let url = `/api/expenses/${id}`;
        return this.http.delete<void>(url);
    }
}
