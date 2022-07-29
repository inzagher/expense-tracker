import { Moment } from 'moment';
import * as moment_dependency from 'moment';

const moment = moment_dependency;

export class DateUtils {
    static createUTCMoment(y: number, m: number, d: number): Moment {
        let date = moment.utc();
        date.set('year', y);
        date.set('month', m);
        date.set('date', d);
        date.startOf('day');
        return date;
    }

    static createUTCDate(y: number, m: number, d: number): Date {
        return new Date(Date.UTC(y, m, d, 0, 0, 0));
    }

    static parseUTCDate(input: string): Date {
        let date = new Date(Date.parse(input));
        let y = date.getFullYear();
        let m = date.getMonth();
        let d = date.getDate();
        return DateUtils.createUTCDate(y, m, d);
    }

    static toUtcDateFromMoment(input: Moment): Date {
        let y = input.year();
        let m = input.month();
        let d = input.date();
        return DateUtils.createUTCDate(y, m, d);
    }

    static areEqual(left: Date  | null, right: Date | null): boolean {
        return left?.getTime() === right?.getTime();
    }
}
