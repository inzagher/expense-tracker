package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.ExpenseDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.Person;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import inzagher.expense.tracker.server.repository.PersonRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    
    public Optional<ExpenseDTO> getExpenseById(String id) {
        UUID uuid = UUID.fromString(id);
        return expenseRepository.findById(uuid).map(Expense::toDTO);
    }
    
    public String storeExpense(ExpenseDTO dto) {
        Expense model;
        if (dto.getId() != null) {
            UUID expenseID = UUID.fromString(dto.getId());
            Optional<Expense> loadedExpense = expenseRepository.findById(expenseID);
            model = loadedExpense.orElseThrow(() -> new RuntimeException("EXPENSE NOT FOUND"));
        } else {
            model = new Expense();
        }
        
        UUID categoryID = UUID.fromString(dto.getCategoryId());
        Optional<Category> loadedCategory = categoryRepository.findById(categoryID);
        Category category = loadedCategory.orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"));
        
        UUID personID = UUID.fromString(dto.getPersonId());
        Optional<Person> loadedPerson = personRepository.findById(personID);
        Person person = loadedPerson.orElseThrow(() -> new RuntimeException("PERSON NOT FOUND"));
        
        model.setDate(dto.getDate());
        model.setAmount(dto.getAmount());
        model.setDescription(dto.getDescription());
        model.setCategory(category);
        model.setPerson(person);
        return expenseRepository.saveAndFlush(model).getId().toString();
    }
    
    public void deleteExpense(String id) {
        UUID uuid = UUID.fromString(id);
        expenseRepository.deleteById(uuid);
    }
}
