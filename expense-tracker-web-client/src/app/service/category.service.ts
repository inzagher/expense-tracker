import * as uuid from 'uuid';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Category } from '../model/category';
import { Color } from '../model/color';

import { MemoryDataService } from './memory-data.service';
import { ObjectUtils } from '../util/object-utils';
import { RxUtils } from '../util/rx-utils';

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
    constructor(private memoryDataService: MemoryDataService) {
        super();
    }

    list(): Observable<Category[]> {
        return RxUtils.asObservable(() =>
            this.memoryDataService.categories.map(c => {
                return ObjectUtils.deepCopy(c);
            })
        );
    }

    getById(id: string): Observable<Category> {
        return RxUtils.asObservable(() => {
            let category = this.memoryDataService.categories.find(p => p.id === id);
            if (category) { return ObjectUtils.deepCopy(category); }
            else { throw 'Category not found.'; }
        });
    }

    save(category: Category): Observable<void> {
        return RxUtils.asObservable(() => {
            let copy = ObjectUtils.deepCopy(category);
            if (copy.id === null) { copy.id = uuid.v4(); }

            let index = this.memoryDataService.categories.findIndex(p => p.id === category.id);
            if (index !== -1) { this.memoryDataService.categories[index] = copy; }
            else { this.memoryDataService.categories.push(copy);  }
        });
    }

    delete(id: string): Observable<void> {
        return RxUtils.asObservable(() => {
            let index = this.memoryDataService.categories.findIndex(x => x.id === id);
            if (index !== -1) { this.memoryDataService.categories.splice(index, 1); }
            else { throw 'Category not found.'; }
        });
    }
}
