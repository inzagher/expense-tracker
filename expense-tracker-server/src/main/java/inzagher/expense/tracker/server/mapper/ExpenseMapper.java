package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExpenseMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "person.id", target = "personId")
    ExpenseDTO toDTO(Expense model);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "personId", target = "person.id")
    Expense toModel(ExpenseDTO model);
}
