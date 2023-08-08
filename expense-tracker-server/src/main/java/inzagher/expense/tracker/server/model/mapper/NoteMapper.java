package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.dto.NoteEditDTO;
import inzagher.expense.tracker.server.model.dto.NoteReadDTO;
import inzagher.expense.tracker.server.model.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface NoteMapper {

    NoteReadDTO toReadDTO(NoteEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "personId", target = "person.id")
    NoteEntity toEntity(NoteEditDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "personId", target = "person.id")
    void mergeToExistingEntity(@MappingTarget NoteEntity entity, NoteEditDTO dto);
}
