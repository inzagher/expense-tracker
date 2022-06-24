import { Moment } from 'moment';
import { MomentDateAdapter, MatMomentDateAdapterOptions } from "@angular/material-moment-adapter";

export class CustomDateAdapter extends MomentDateAdapter {
    constructor(dateLocale: string, _options?: MatMomentDateAdapterOptions | undefined) {
        super(dateLocale, _options);
    }

    format(date: Moment, displayFormat: string): string {
        return super.format(date, displayFormat).toLocaleUpperCase();
    }
}
