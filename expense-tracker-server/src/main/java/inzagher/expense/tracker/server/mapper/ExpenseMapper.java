package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.command.CreateExpenseCommand;
import inzagher.expense.tracker.server.command.EditExpenseCommand;
import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExpenseMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "person.id", target = "personId")
    ExpenseDTO toDTO(Expense model);
    ExpenseFilter toFilter(ExpenseFilterDTO dto);
    CreateExpenseCommand toCreateCommand(ExpenseDTO dto);
    EditExpenseCommand toEditCommand(ExpenseDTO dto);
}
