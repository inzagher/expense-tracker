import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Person } from '../model/person';
import { MemoryDataService } from './memory-data.service';
import { ObjectUtils } from '../util/object-utils';
import { RxUtils } from '../util/rx-utils';

export abstract class PersonService {
    abstract list(): Observable<Person[]>;
    abstract getById(id: number): Observable<Person>;
    abstract save(person: Person): Observable<void>;
    abstract delete(id: number): Observable<void>;
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

    getById(id: number): Observable<Person> {
        return this.http.get('/api/persons/' + id).pipe(
            map(dto => this.toPerson(dto))
        );
    }

    save(person: Person): Observable<void> {
        let json = JSON.parse(JSON.stringify(person));
        return this.http.post('/api/persons', json).pipe(
            map(response => {})
        );
    }

    delete(id: number): Observable<void> {
        return this.http.delete('/api/persons/' + id).pipe(
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

    getById(id: number): Observable<Person> {
        return RxUtils.asObservable(() => {
            let person = this.memoryDataService.persons.find(p => p.id === id);
            if (person) { return ObjectUtils.deepCopy(person); }
            else { throw 'Person not found.'; }
        });
    }

    save(person: Person): Observable<void> {
        return RxUtils.asObservable(() => {
            let copy = ObjectUtils.deepCopy(person);
            if (copy.id === null) { copy.id = this.memoryDataService.nextPersonId(); }

            let index = this.memoryDataService.persons.findIndex(p => p.id === person.id);
            if (index !== -1) { this.memoryDataService.persons[index] = copy; }
            else { this.memoryDataService.persons.push(copy);  }
        });
    }

    delete(id: number): Observable<void> {
        return RxUtils.asObservable(() => {
            let index = this.memoryDataService.persons.findIndex(p => p.id === id);
            if (index !== -1) { this.memoryDataService.persons.splice(index, 1); }
            else { throw 'Person not found.'; }
        });
    }
}
