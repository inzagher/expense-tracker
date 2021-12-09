package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDTO(Category model);
    Category toModel(CategoryDTO dto);
}
