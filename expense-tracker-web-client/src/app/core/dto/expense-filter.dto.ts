export interface ExpenseFilterDTO {
    categories?: number[];
    persons?: number[];
    amountExact?: number;
    amountFrom?: number;
    amountTo?: number;
    dateFrom?: string;
    dateTo?: string;
    description?: string;
}
