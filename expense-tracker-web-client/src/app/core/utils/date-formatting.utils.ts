export class DateFormattingUtils {
    static getDateFormatSettings(): any {
        let delimiters = ['.', '.', '/', ' ']
        let formats = delimiters.map(d => 'DD' + d + 'MM' + d + 'YYYY').concat(
                      delimiters.map(d => 'DD' + d + 'MMM' + d + 'YYYY'));
        return {
            parse: { dateInput: formats },
            display: {
                dateInput: 'custom',
                monthYearLabel: 'MMM YYYY',
                dateA11yLabel: 'LL',
                monthYearA11yLabel: 'MMMM YYYY',
            }
        }
    }
}
