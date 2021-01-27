import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Category } from '../model/category';
import { Color } from '../model/color';

export abstract class CategoryService {
    abstract list(): Observable<Category[]>;
    abstract getById(id: string): Observable<Category>;
    abstract save(category: Category): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpCategoryService extends CategoryService {
    constructor(private http: HttpClient) {
        super();
    }

    list(): Observable<Category[]> {
        return this.http.get('/api/categories').pipe(
            map((list: any[]) => list.map(dto => this.toCategory(dto)))
        );
    }

    getById(id: string): Observable<Category> {
        let parameters = new HttpParams().append('id', id);
        return this.http.get('/api/categories', { params: parameters }).pipe(
            map(dto => this.toCategory(dto))
        );
    }

    save(category: Category): Observable<void> {
        let json = JSON.parse(JSON.stringify(category));
        return this.http.post('/api/categories', json).pipe(
            map(response => null)
        );
    }

    delete(id: string): Observable<void> {
        let parameters = new HttpParams().append('id', id);
        return this.http.delete('/api/categories', { params: parameters }).pipe(
            map(response => null)
        );
    }

    private toCategory(dto): Category {
        let category = new Category();
        category.id = dto.id;
        category.name = dto.name;
        category.color = new Color();
        category.color.red = dto.color.red;
        category.color.green = dto.color.green;
        category.color.blue = dto.color.blue;
        category.description = dto.description;
        return category;
    }
}
