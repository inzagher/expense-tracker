package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.command.CreateCategoryCommand;
import inzagher.expense.tracker.server.command.EditCategoryCommand;
import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDTO(Category model);
    @Mapping(source = "color.red", target = "colorRed")
    @Mapping(source = "color.green", target = "colorGreen")
    @Mapping(source = "color.blue", target = "colorBlue")
    CreateCategoryCommand toCreateCommand(CategoryDTO dto);
    @Mapping(source = "color.red", target = "colorRed")
    @Mapping(source = "color.green", target = "colorGreen")
    @Mapping(source = "color.blue", target = "colorBlue")
    EditCategoryCommand toEditCommand(CategoryDTO dto);
}
