import { PersonDTO, CategoryDTO } from "@core/dto";

export interface ExpenseDTO {
    id: number | null;
    person: PersonDTO | null;
    category: CategoryDTO | null;
    date: string | null;
    amount: number | null;
    description: string | null;
}
