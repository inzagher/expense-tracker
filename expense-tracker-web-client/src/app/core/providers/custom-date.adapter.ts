import { Moment } from 'moment';
import { MomentDateAdapter, MatMomentDateAdapterOptions } from "@angular/material-moment-adapter";

export class CustomDateAdapter extends MomentDateAdapter {
    constructor(private dateLocale: string, _options?: MatMomentDateAdapterOptions | undefined) {
        super(dateLocale, _options);
    }

    format(date: Moment, displayFormat: string): string {
        if (displayFormat === 'custom') {
            let day = super.format(date, 'DD');
            let month = super.format(date, 'MMM');
            let year = super.format(date, 'YYYY');
            if (month.endsWith('.')) {
                month = month.substr(0, month.length - 1);
            }
            if (this.dateLocale === 'ru-RU') {
                month = month.substr(0, 3);
            }
            return day + '.' + month + '.' + year;
        }
        return super.format(date, displayFormat);
    }
}
