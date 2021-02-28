export class Expense {
    public id: number | null = null;
    public date: Date = new Date();
    public amount: number = 0;
    public personId: number | null = null;
    public categoryId: number | null = null;
    public description: string = '';
}
