package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.dto.PersonDTO;
import inzagher.expense.tracker.server.model.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDTO toDTO(Person model);
    Person toModel(PersonDTO dto);
}
