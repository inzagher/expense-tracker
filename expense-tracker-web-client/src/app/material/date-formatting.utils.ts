export class DateFormattingUtils {
    static getDateFormatSettings(): any {
        let delimiters = ['.', '.', '/', ' ']
        let formats = delimiters.map(d => 'DD' + d + 'MM' + d + 'YYYY').concat(
                      delimiters.map(d => 'DD' + d + 'MMM' + d + 'YYYY'));
        return {
            parse: { dateInput: formats },
            display: {
                dateInput: 'DD.MM.YYYY',
                monthYearLabel: 'MMM YYYY',
                dateA11yLabel: 'LL',
                monthYearA11yLabel: 'MMMM YYYY',
            }
        }
    }

    static getYearMonthFormatSettings(): any {
        return {
            parse: { dateInput: 'MM.YYYY' },
            display: {
                dateInput: 'MMMM YYYY',
                monthYearLabel: 'MMM YYYY',
                dateA11yLabel: 'LL',
                monthYearA11yLabel: 'MMMM YYYY',
            }
        }
    }
}
