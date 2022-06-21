export class DateUtils {
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
}
