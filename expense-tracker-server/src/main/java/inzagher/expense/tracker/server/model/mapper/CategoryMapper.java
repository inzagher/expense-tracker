package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.dto.backup.CategoryXmlItemDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import org.mapstruct.*;

@Mapper
public interface CategoryMapper {
    CategoryDTO toDTO(CategoryEntity entity);
    CategoryXmlItemDTO toXmlDTO(CategoryEntity entity);
    CategoryEntity toEntity(CategoryDTO dto);
    CategoryEntity toEntity(CategoryXmlItemDTO dto);
    @Mapping(target = "id", ignore = true)
    void mergeToExistingEntity(@MappingTarget CategoryEntity entity, CategoryDTO dto);
}
