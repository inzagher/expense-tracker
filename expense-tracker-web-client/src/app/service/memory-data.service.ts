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
        this.persons.push({ id: 0, name: 'Tom' });
        this.persons.push({ id: 1, name: 'Bob' });
        this.persons.push({ id: 2, name: 'Alice' });

        this.categories.splice(0, this.categories.length);
        this.categories.push({ id: 0, name: 'FOOD', color: { red: 255, green: 255, blue: 255 }, description: 'DAILY FOOD EXPENSES', obsolete: false });
        this.categories.push({ id: 1, name: 'RENT', color: { red: 255, green: 0, blue: 0 }, description: 'MONTHLY RENT PAYMENTS', obsolete: false });
        this.categories.push({ id: 2, name: 'PHONE', color: { red: 255, green: 255, blue: 0 }, description: 'MONTHLY RENT PAYMENTS', obsolete: false });
        this.categories.push({ id: 3, name: 'EDUCATION', color: { red: 0, green: 255, blue: 0 }, description: 'YEARLY EDUCATION PAYMENTS', obsolete: false });

        this.backups.splice(0, this.backups.length);
        this.backups.push({ id: 0, time: new Date(), expenses: this.expenses.length, categories: this.categories.length, persons: this.persons.length });
    }

    nextBackupId(): number {
        return this.nextId(this.backups);
    }

    nextPersonId(): number {
        return this.nextId(this.persons);
    }

    nextCategoryId(): number {
        return this.nextId(this.categories);
    }

    nextExpenseId(): number {
        return this.nextId(this.expenses);
    }

    private nextId<T>(array: Array<T>): number {
        return array.filter((element: any) => !!element.id).length > 0
            ? Math.max(...array.map(b => (b as any).id)) + 1
            : 0;
    }
}
