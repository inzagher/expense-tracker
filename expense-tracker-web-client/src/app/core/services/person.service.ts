import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { PersonDTO } from "@core/dto";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class PersonService {
    constructor(private http: HttpClient) {
    }

    findAll(): Observable<PersonDTO[]> {
        let url = `/api/persons`;
        return this.http.get<PersonDTO[]>(url);
    }

    getById(id: number): Observable<PersonDTO> {
        let url = `/api/persons/${id}`;
        return this.http.get<PersonDTO>(url);
    }

    create(person: PersonDTO): Observable<void> {
        let url = '/api/persons';
        return this.http.post<void>(url, person);
    }

    edit(person: PersonDTO): Observable<void> {
        let url = `/api/persons/${person.id}`;
        return this.http.put<void>(url, person);
    }

    deleteById(id: number): Observable<void> {
        let url = `/api/persons/${id}`;
        return this.http.delete<void>(url);
    }
}
