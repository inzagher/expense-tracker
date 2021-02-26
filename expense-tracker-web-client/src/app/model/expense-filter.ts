export class ExpenseFilter {

    public categoryIdentifiers: number[] = [];
    public personIdentifiers: number[] = [];
    public amountExact: number | null = null;
    public amountFrom: number | null = null;
    public amountTo: number | null = null;
    public dateExact: Date | null = null;
    public dateFrom: Date | null = null;
    public dateTo: Date | null = null;
    public descriptionLike: string | null = null;
}
