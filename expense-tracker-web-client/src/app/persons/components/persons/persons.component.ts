import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChangeTitleCommand } from '@core/commands';
import { PersonDTO } from '@core/dto';
import { Bus, PersonService } from '@core/services';
import { DialogService } from '@core/services/dialog.service';
import { BehaviorSubject, Observable, switchMap } from 'rxjs';

@Component({
    selector: 'persons',
    templateUrl: './persons.component.html',
    styleUrls: ['./persons.component.scss']
})
export class PersonsComponent implements OnInit, OnDestroy {
    public persons$: Observable<PersonDTO[]> | null = null;
    private update: BehaviorSubject<void> = new BehaviorSubject<void>(void 0);

    constructor(private bus: Bus,
                private router: Router,
                private dialogService: DialogService,
                private personService: PersonService) { }

    ngOnInit(): void {
        this.persons$ = this.update.pipe(switchMap(() => this.personService.findAll()));
        this.bus.publish(new ChangeTitleCommand("Пользователи"));
    }

    ngOnDestroy(): void {
        this.update.complete();
    }

    refresh(): void {
        this.update.next();
    }

    add(): void {
        this.router.navigate(['persons/editor'])
    }

    edit(person: PersonDTO): void {
        this.router.navigate(['persons/editor/' + person.id]);
    }

    delete(person: PersonDTO): void {
        let caption = 'Внимание!';
        let question = 'Вы действительно хотите удалить пользователя?';
        let request$ = this.personService.deleteById(person.id as number);
        this.dialogService.confirmAndExecute(caption, question, request$).subscribe({
            next: () => { this.refresh(); },
            error: (error) => { console.log(error); }
        });
    }
}
