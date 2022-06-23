package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.dto.backup.PersonXmlItemDTO;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import org.mapstruct.*;

@Mapper
public interface PersonMapper {
    PersonDTO toDTO(PersonEntity entity);
    PersonXmlItemDTO toXmlDTO(PersonEntity entity);

    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(PersonDTO dto);

    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(PersonXmlItemDTO dto);

    @Mapping(target = "id", ignore = true)
    void mergeToExistingEntity(@MappingTarget PersonEntity entity, PersonDTO dto);
}
