import { Injectable, OnDestroy } from "@angular/core";
import { ApplicationEvent } from "@core/events";
import { Observable, Subject } from "rxjs";

@Injectable({ providedIn: 'root' })
export class EventBus implements OnDestroy {
    private subject = new Subject<ApplicationEvent>();

    get events$(): Observable<ApplicationEvent> {
        return this.subject.asObservable();
    }

    publish<TEvent extends ApplicationEvent>(event: TEvent): void {
        this.subject.next(event);
    }

    ngOnDestroy(): void {
        this.subject.complete();
    }
}
