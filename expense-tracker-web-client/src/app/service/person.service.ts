import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Person } from '../model/person';

import { MemoryDataAccessService } from './memory-data-access.service';
import { ObjectCloneService } from './object-clone.service';

export abstract class PersonDataAccessService {
    abstract list(): Observable<Person[]>;
    abstract getById(id: string): Observable<Person>;
    abstract save(person: Person): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpPersonDataAccessService extends PersonDataAccessService {
    constructor(private http: HttpClient) {
        super();
    }

    list(): Observable<Person[]> {
        return this.http.get<any[]>('/api/persons').pipe(
            map((list: any[]) => list.map(dto => this.toPerson(dto)))
        );
    }

    getById(id: string): Observable<Person> {
        let parameters = new HttpParams().append('id', id);
        return this.http.get('/api/persons', { params: parameters }).pipe(
            map(dto => this.toPerson(dto))
        );
    }

    save(person: Person): Observable<void> {
        let json = JSON.parse(JSON.stringify(person));
        return this.http.post('/api/persons', json).pipe(
            map(response => {})
        );
    }

    delete(id: string): Observable<void> {
        let parameters = new HttpParams().append('id', id);
        return this.http.delete('/api/persons', { params: parameters }).pipe(
            map(response => {})
        );
    }

    private toPerson(dto: any): Person {
        let person = new Person();
        person.id = dto.id;
        person.name = dto.name;
        return person;
    }
}

@Injectable({ providedIn: 'root' })
export class StubPersonDataAccessService extends PersonDataAccessService {
    constructor(
        private memoryDataService: MemoryDataAccessService,
        private objectCloneService: ObjectCloneService,
    ) { super(); }

    list(): Observable<Person[]> {
        return of(this.memoryDataService.persons.map(p => {
            return this.objectCloneService.deepCopy<Person>(p);
        }));
    }

    getById(id: string): Observable<Person> {
        return new Observable<Person>((observer) => {
            let person = this.memoryDataService.persons
                .map(p => this.objectCloneService.deepCopy<Person>(p))
                .find(p => p.id === id);

            if (person) { observer.next(person); observer.complete(); }
            else { observer.error('Person not found.'); }
        });
    }

    save(person: Person): Observable<void> {
        throw new Error('Method not implemented.');
    }

    delete(id: string): Observable<void> {
        throw new Error('Method not implemented.');
    }
}
