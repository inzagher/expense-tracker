import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Category } from '../model/category';
import { Color } from '../model/color';

import { MemoryDataAccessService } from './memory-data-access.service';
import { ObjectCloneService } from './object-clone.service';

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
        return this.http.get<any[]>('/api/categories').pipe(
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
            map(response => { })
        );
    }

    delete(id: string): Observable<void> {
        let parameters = new HttpParams().append('id', id);
        return this.http.delete('/api/categories', { params: parameters }).pipe(
            map(response => { })
        );
    }

    private toCategory(dto: any): Category {
        let category = new Category();
        category.id = dto.id;
        category.name = dto.name;
        category.color = new Color();
        category.color.red = dto.color.red;
        category.color.green = dto.color.green;
        category.color.blue = dto.color.blue;
        category.description = dto.description;
        category.obsolete = dto.obsolete;
        return category;
    }
}

@Injectable({ providedIn: 'root' })
export class StubCategoryService extends CategoryService {
    constructor(
        private memoryDataService: MemoryDataAccessService,
        private objectCloneService: ObjectCloneService,
    ) { super(); }

    list(): Observable<Category[]> {
        return of(this.memoryDataService.categories.map(p => {
            return this.objectCloneService.deepCopy<Category>(p);
        }));
    }

    getById(id: string): Observable<Category> {
        return new Observable<Category>((observer) => {
            let person = this.memoryDataService.categories
                .map(p => this.objectCloneService.deepCopy<Category>(p))
                .find(p => p.id === id);

            if (person) { observer.next(person); observer.complete(); }
            else { observer.error('Category not found.'); }
        });
    }

    save(category: Category): Observable<void> {
        throw new Error('Method not implemented.');
    }

    delete(id: string): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
