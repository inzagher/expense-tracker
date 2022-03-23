package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.command.CreatePersonCommand;
import inzagher.expense.tracker.server.model.command.EditPersonCommand;
import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.model.entity.PersonEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDTO toDTO(PersonEntity entity);
    PersonEntity toModel(PersonDTO dto);
    CreatePersonCommand toCreateCommand(PersonDTO dto);
    EditPersonCommand toEditCommand(PersonDTO dto);
}
