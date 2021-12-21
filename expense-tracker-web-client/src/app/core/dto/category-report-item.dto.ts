import { CategoryDTO } from "@core/dto";

export interface CategoryReportItemDTO {
    category: CategoryDTO | null;
    amount: number | null;
}
