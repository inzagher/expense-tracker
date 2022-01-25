import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketListener } from '@core/services';

@Component({
    selector: 'layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit, OnDestroy {
    constructor(private webSocketListener: WebSocketListener) { }

    ngOnInit(): void {
        this.webSocketListener.connect();
    }

    ngOnDestroy(): void {
        this.webSocketListener.disconnect();
    }
}
