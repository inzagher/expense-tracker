import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Person } from '../model/person';

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
        return this.http.get('/api/persons').pipe(
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
            map(response => null)
        );
    }

    delete(id: string): Observable<void> {
        let parameters = new HttpParams().append('id', id);
        return this.http.delete('/api/persons', { params: parameters }).pipe(
            map(response => null)
        );
    }

    private toPerson(dto: any): Person {
        let person = new Person();
        person.id = dto.id;
        person.name = dto.name;
        return person;
    }
}
