package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final ExpenseMapper expenseMapper;

    public List<ExpenseDTO> findExpenses(ExpenseFilterDTO dto) {
        ExpenseFilter filter = new ExpenseFilter();
        filter.setAmountFrom(dto.getAmountTo());
        filter.setAmountTo(dto.getAmountTo());
        filter.setAmountExact(dto.getAmountExact());
        filter.setDateExact(dto.getDateExact());
        filter.setDateFrom(dto.getDateFrom());
        filter.setDateTo(dto.getDateTo());
        filter.setDescriptionLike(dto.getDescriptionLike());
        dto.getCategoryIdentifiers().forEach(filter.getCategoryIdentifiers()::add);
        dto.getPersonIdentifiers().forEach(filter.getPersonIdentifiers()::add);
        return expenseRepository.find(filter).stream()
                .map(expenseMapper::toDTO)
                .toList();
    }
    
    public Optional<ExpenseDTO> getExpenseById(Integer id) {
        return expenseRepository.findById(id).map(expenseMapper::toDTO);
    }
    
    public Integer storeExpense(ExpenseDTO dto) {
        Expense model;
        if (dto.getId() != null) {
            Optional<Expense> loadedExpense = expenseRepository.findById(dto.getId());
            model = loadedExpense.orElseThrow(() -> new RuntimeException("EXPENSE NOT FOUND"));
        } else {
            model = new Expense();
        }
        
        Optional<Category> loadedCategory = categoryRepository.findById(dto.getCategoryId());
        Category category = loadedCategory.orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"));
        
        Optional<Person> loadedPerson = personRepository.findById(dto.getPersonId());
        Person person = loadedPerson.orElseThrow(() -> new RuntimeException("PERSON NOT FOUND"));
        
        model.setDate(dto.getDate());
        model.setAmount(dto.getAmount());
        model.setDescription(dto.getDescription());
        model.setCategory(category);
        model.setPerson(person);
        return expenseRepository.saveAndFlush(model).getId();
    }
    
    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }
}
