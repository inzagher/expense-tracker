import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../model/category';

export abstract class CategoryDataAccessService {
    abstract list(): Observable<Category[]>;
    abstract getById(id: string): Observable<Category>;
    abstract save(category: Category): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpCategoryDataAccessService extends CategoryDataAccessService {
    list(): Observable<Category[]> {
        throw new Error('Method not implemented.');
    }
    getById(id: string): Observable<Category> {
        throw new Error('Method not implemented.');
    }
    save(category: Category): Observable<void> {
        throw new Error('Method not implemented.');
    }
    delete(id: string): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
