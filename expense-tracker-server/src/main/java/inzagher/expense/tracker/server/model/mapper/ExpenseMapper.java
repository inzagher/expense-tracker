package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.command.CreateExpenseCommand;
import inzagher.expense.tracker.server.model.command.EditExpenseCommand;
import inzagher.expense.tracker.server.model.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExpenseMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "person.id", target = "personId")
    ExpenseDTO toDTO(ExpenseEntity model);
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "personId", target = "person.id")
    ExpenseEntity toModel(ExpenseDTO dto);
    CreateExpenseCommand toCreateCommand(ExpenseDTO dto);
    EditExpenseCommand toEditCommand(ExpenseDTO dto);
}
