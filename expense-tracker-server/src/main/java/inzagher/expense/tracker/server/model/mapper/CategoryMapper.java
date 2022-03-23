package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.command.CreateCategoryCommand;
import inzagher.expense.tracker.server.model.command.EditCategoryCommand;
import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDTO(CategoryEntity model);
    CategoryEntity toModel(CategoryDTO dto);
    @Mapping(source = "color.red", target = "colorRed")
    @Mapping(source = "color.green", target = "colorGreen")
    @Mapping(source = "color.blue", target = "colorBlue")
    CreateCategoryCommand toCreateCommand(CategoryDTO dto);
    @Mapping(source = "color.red", target = "colorRed")
    @Mapping(source = "color.green", target = "colorGreen")
    @Mapping(source = "color.blue", target = "colorBlue")
    EditCategoryCommand toEditCommand(CategoryDTO dto);
}
