import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CategoryDTO } from "@core/dto";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class CategoryService {
    constructor(private http: HttpClient) {
    }

    findAll(): Observable<CategoryDTO[]> {
        let url = '/api/categories';
        return this.http.get<CategoryDTO[]>(url);
    }

    getById(id: number): Observable<CategoryDTO> {
        let url = `/api/categories/${id}`;
        return this.http.get<CategoryDTO>(url);
    }

    create(category: CategoryDTO): Observable<void> {
        let url = '/api/categories';
        return this.http.post<void>(url, category);
    }

    edit(category: CategoryDTO): Observable<void> {
        let url = `/api/categories/${category.id}`;
        return this.http.put<void>(url, category);
    }

    deleteById(id: number): Observable<void> {
        let url = `/api/categories/${id}`;
        return this.http.delete<void>(url);
    }
}
