import { Injectable } from "@angular/core";
import { BackupMetadata } from "../model/backup-metadata";

import { Category } from "../model/category";
import { Expense } from "../model/expense";
import { Person } from "../model/person";

@Injectable({ providedIn: 'root' })
export class MemoryDataService {
    public readonly expenses: Expense[] = [];
    public readonly persons: Person[] = [];
    public readonly categories: Category[] = [];
    public readonly backups: BackupMetadata[] = [];

    constructor() {
        this.generateTestData();
    }

    generateTestData(): void {
        this.persons.splice(0, this.persons.length);
        this.persons.push({ id: 'bc9d24ea-a0a8-4ce5-82af-1043ea126bfe', name: 'Tom' });
        this.persons.push({ id: 'cc48334a-0a72-4547-a78a-9de73f49484e', name: 'Bob' });
        this.persons.push({ id: 'd623f047-fc37-472b-a120-f7a4503bf6a0', name: 'Alice' });

        this.categories.splice(0, this.persons.length);
        this.categories.push({ id: '7190ceea-3980-446f-a238-c8f9510aa26c', name: 'FOOD', color: { red: 255, green: 255, blue: 255 }, description: 'DAILY FOOD EXPENSES', obsolete: false });
        this.categories.push({ id: '17011461-2238-4e97-8df2-ead48c7fa827', name: 'RENT', color: { red: 255, green: 0, blue: 0 }, description: 'MONTHLY RENT PAYMENTS', obsolete: false });
        this.categories.push({ id: 'd623f047-fc37-472b-a120-f7a4503bf6a0', name: 'PHONE', color: { red: 255, green: 255, blue: 0 }, description: 'MONTHLY RENT PAYMENTS', obsolete: false });
        this.categories.push({ id: 'a044e90c-3fe5-41be-97bf-cfed367597f5', name: 'EDUCATION', color: { red: 0, green: 255, blue: 0 }, description: 'YEARLY EDUCATION PAYMENTS', obsolete: false });

        this.backups.splice(0, this.persons.length);
        this.backups.push({
            id: '4166018e-7eb4-411e-81dd-a13c90830a12',
            time: new Date(),
            expenses: this.expenses.length,
            categories: this.categories.length,
            persons: this.persons.length
        });
    }
}
