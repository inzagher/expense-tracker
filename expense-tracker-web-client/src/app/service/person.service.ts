import * as uuid from 'uuid';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Person } from '../model/person';
import { MemoryDataService } from './memory-data.service';
import { ObjectUtils } from '../util/object-utils';
import { RxUtils } from '../util/rx-utils';

export abstract class PersonService {
    abstract list(): Observable<Person[]>;
    abstract getById(id: string): Observable<Person>;
    abstract save(person: Person): Observable<void>;
    abstract delete(id: string): Observable<void>;
}

@Injectable({ providedIn: 'root' })
export class HttpPersonDataAccessService extends PersonService {
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
export class StubPersonDataAccessService extends PersonService {
    constructor(private memoryDataService: MemoryDataService) {
        super();
    }

    list(): Observable<Person[]> {
        return RxUtils.asObservable(() =>
            this.memoryDataService.persons
                .map(p => { return ObjectUtils.deepCopy(p); })
        );
    }

    getById(id: string): Observable<Person> {
        return RxUtils.asObservable(() => {
            let person = this.memoryDataService.persons.find(p => p.id === id);
            if (person) { return ObjectUtils.deepCopy(person); }
            else { throw 'Person not found.'; }
        });
    }

    save(person: Person): Observable<void> {
        return RxUtils.asObservable(() => {
            let copy = ObjectUtils.deepCopy(person);
            if (copy.id === null) { copy.id = uuid.v4(); }

            let index = this.memoryDataService.persons.findIndex(p => p.id === person.id);
            if (index !== -1) { this.memoryDataService.persons[index] = copy; }
            else { this.memoryDataService.persons.push(copy);  }
        });
    }

    delete(id: string): Observable<void> {
        return RxUtils.asObservable(() => {
            let index = this.memoryDataService.persons.findIndex(p => p.id === id);
            if (index !== -1) { this.memoryDataService.persons.splice(index, 1); }
            else { throw 'Person not found.'; }
        });
    }
}
