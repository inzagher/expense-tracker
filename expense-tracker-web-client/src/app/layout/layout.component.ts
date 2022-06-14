import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChangeTitleCommand } from '@core/commands';
import { Bus, WebSocketListener } from '@core/services';
import { debounceTime, filter, map, Observable } from 'rxjs';

@Component({
    selector: 'layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit, OnDestroy {
    title$: Observable<string> | null = null;

    constructor(private bus: Bus,
                private webSocketListener: WebSocketListener) { }

    ngOnInit(): void {
        this.webSocketListener.connect();
        this.title$ = this.bus.messages$.pipe(
            filter(m => m instanceof ChangeTitleCommand),
            map(m => m as ChangeTitleCommand),
            map(m => m.title),
            debounceTime(1)
        );
    }

    ngOnDestroy(): void {
        this.webSocketListener.disconnect();
    }
}
