import { Injectable, OnDestroy } from "@angular/core";
import { BusCommand } from "@core/commands";
import { BusEvent } from "@core/events";
import { Observable, Subject } from "rxjs";

export type BusMessage = BusEvent | BusCommand;

@Injectable({ providedIn: 'root' })
export class Bus implements OnDestroy {
    private subject = new Subject<BusMessage>();

    get messages$(): Observable<BusMessage> {
        return this.subject.asObservable();
    }

    publish(message: BusMessage): void {
        this.subject.next(message);
    }

    ngOnDestroy(): void {
        this.subject.complete();
    }
}
