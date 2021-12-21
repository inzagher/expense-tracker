import { ColorDTO } from "@core/dto";

export interface CategoryDTO {
    id: number | null;
    name: string | null;
    color: ColorDTO | null;
    description: string | null;
    obsolete: boolean | null;
}
