import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { IMessage } from '@stomp/stompjs';
import { EventBus } from '@core/services';
import { ApplicationEvent, BackupCreatedEvent, BackupRestoredEvent } from '@core/events';

@Injectable({ providedIn: 'root' })
export class WebSocketListener {
    private stomp: RxStomp | null = null;
    private subscription: Subscription | null = null;

    constructor(private bus: EventBus) {
    }

    connect(): void {
        this.stomp = new RxStomp();
        this.stomp.configure(this.createDefaultConfig());
        this.subscription = this.stomp.watch('/queue/messages').subscribe(m => this.onMessageReceived(m));
        this.stomp.activate();
    }

    disconnect(): void {
        if (this.stomp) {
            this.stomp.deactivate();
            this.stomp = null;
        }
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
    }

    private onMessageReceived(message: IMessage): void {
        let event = this.deserialize(message.body);
        if (event) {
            this.bus.publish(event);
        }
    }

    private createDefaultConfig(): RxStompConfig {
        return {
            brokerURL: this.prepareWebSocketURL(),
            heartbeatIncoming: 0,
            heartbeatOutgoing: 0,
            reconnectDelay: 60000
        };
    }

    private prepareWebSocketURL(): string {
        let host = window.location.host;
        let protocol = window.location.protocol === 'https:' ? 'wss' : 'ws';
        return `${protocol}://${host}/notifications/websocket`;
    }

    private deserialize(data: string): ApplicationEvent | null {
        switch(data) {
            case 'BackupCreatedEvent': return new BackupCreatedEvent();
            case 'BackupRestoredEvent': return new BackupRestoredEvent();
            default: return null;
        }
    }
}
