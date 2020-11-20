import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Person } from '../model/person';

export abstract class PersonDataAccessService {
    abstract list(): Observable<Person[]>;
    abstract getById(id: string): Observable<Person>;
    abstract save(person: Person): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpPersonDataAccessService extends PersonDataAccessService {
    list(): Observable<Person[]> {
        throw new Error('Method not implemented.');
    }
    getById(id: string): Observable<Person> {
        throw new Error('Method not implemented.');
    }
    save(person: Person): Observable<void> {
        throw new Error('Method not implemented.');
    }
    delete(id: string): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
