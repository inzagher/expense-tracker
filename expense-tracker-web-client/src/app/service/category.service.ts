import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Category } from '../model/category';
import { Color } from '../model/color';

import { MemoryDataService } from './memory-data.service';
import { ObjectUtils } from '../util/object-utils';
import { RxUtils } from '../util/rx-utils';

export abstract class CategoryService {
    abstract list(): Observable<Category[]>;
    abstract getById(id: number): Observable<Category>;
    abstract save(category: Category): Observable<void>;
    abstract delete(id: number): Observable<void>;
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

    getById(id: number): Observable<Category> {
        return this.http.get('/api/categories/' + id).pipe(
            map(dto => this.toCategory(dto))
        );
    }

    save(category: Category): Observable<void> {
        let json = JSON.parse(JSON.stringify(category));
        return this.http.post('/api/categories', json).pipe(
            map(response => { })
        );
    }

    delete(id: number): Observable<void> {
        return this.http.delete('/api/categories/' + id).pipe(
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

    getById(id: number): Observable<Category> {
        return RxUtils.asObservable(() => {
            let category = this.memoryDataService.categories.find(p => p.id === id);
            if (category) { return ObjectUtils.deepCopy(category); }
            else { throw 'Category not found.'; }
        });
    }

    save(category: Category): Observable<void> {
        return RxUtils.asObservable(() => {
            let copy = ObjectUtils.deepCopy(category);
            if (copy.id === null) { copy.id = this.memoryDataService.nextCategoryId(); }

            let index = this.memoryDataService.categories.findIndex(p => p.id === category.id);
            if (index !== -1) { this.memoryDataService.categories[index] = copy; }
            else { this.memoryDataService.categories.push(copy);  }
        });
    }

    delete(id: number): Observable<void> {
        return RxUtils.asObservable(() => {
            let index = this.memoryDataService.categories.findIndex(x => x.id === id);
            if (index !== -1) { this.memoryDataService.categories.splice(index, 1); }
            else { throw 'Category not found.'; }
        });
    }
}
