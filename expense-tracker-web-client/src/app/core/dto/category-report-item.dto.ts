import { CategoryDTO } from "@core/dto";

export interface CategoryReportItemDTO {
    category: CategoryDTO | null;
    total: number | null;
}
