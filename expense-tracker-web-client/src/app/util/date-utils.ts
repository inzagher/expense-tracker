export class DateUtils {
    static daysInMonth(date: Date): number {
        let month = date.getMonth();
        let year = date.getFullYear();
        return new Date(year, month + 1, 0).getDate();
    }
}
