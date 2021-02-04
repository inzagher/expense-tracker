export class Expense {
    public id: string | null = null;
    public date: Date = new Date();
    public amount: number = 0;
    public personId: string = '';
    public categoryId: string = '';
    public comment: string = '';
}
