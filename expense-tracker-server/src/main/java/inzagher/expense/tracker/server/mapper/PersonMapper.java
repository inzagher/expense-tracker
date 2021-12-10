package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.command.CreatePersonCommand;
import inzagher.expense.tracker.server.command.EditPersonCommand;
import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDTO toDTO(Person entity);
    CreatePersonCommand toCreateCommand(PersonDTO dto);
    EditPersonCommand toEditCommand(PersonDTO dto);
}
