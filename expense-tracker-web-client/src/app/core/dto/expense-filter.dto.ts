export interface ExpenseFilterDTO {
    category?: number[];
    person?: number[];
    amountExact?: number;
    amountFrom?: number;
    amountTo?: number;
    dateFrom?: string;
    dateTo?: string;
    description?: string;
}
