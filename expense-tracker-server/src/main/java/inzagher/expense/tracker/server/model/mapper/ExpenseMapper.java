package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.dto.backup.ExpenseXmlItemDTO;
import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import org.mapstruct.*;

@Mapper
public interface ExpenseMapper {
    ExpenseDTO toDTO(ExpenseEntity entity);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "person.id", target = "personId")
    ExpenseXmlItemDTO toXmlDTO(ExpenseEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "personId", target = "person.id")
    ExpenseEntity toEntity(ExpenseXmlItemDTO dto);

    @Mapping(target = "id", ignore = true)
    ExpenseEntity toEntity(ExpenseDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", qualifiedByName = "ToEmptyPersonEntity")
    @Mapping(target = "category", qualifiedByName = "ToEmptyCategoryEntity")
    void mergeToExistingEntity(@MappingTarget ExpenseEntity entity, ExpenseDTO dto);

    @Named("ToEmptyPersonEntity")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    PersonEntity toEmptyPersonEntity(PersonDTO dto);

    @Named("ToEmptyCategoryEntity")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    CategoryEntity toEmptyCategoryEntity(CategoryDTO dto);
}
