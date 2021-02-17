package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.dto.ExpenseFilterDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    
    @Autowired
    public ExpenseService(
            ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository,
            PersonRepository personRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
    }

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
                .map(Expense::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<ExpenseDTO> getExpenseById(Integer id) {
        return expenseRepository.findById(id).map(Expense::toDTO);
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
