export interface ExpenseFilterDTO {
    category?: number[];
    person?: number[];
    amountFrom?: number;
    amountTo?: number;
    dateFrom?: string;
    dateTo?: string;
    description?: string;
}
