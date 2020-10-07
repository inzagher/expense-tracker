package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public Optional<CategoryDTO> getCategory(String id) {
        UUID uuid = UUID.fromString(id);
        return categoryRepository.findById(uuid).map(Category::toDTO);
    }
    
    public String storeCategory(CategoryDTO dto) {
        Category model;
        byte red = dto.getColor().getRed();
        byte green = dto.getColor().getGreen();
        byte blue = dto.getColor().getBlue();
        if (dto.getId() != null) {
            UUID id = UUID.fromString(dto.getId());
            Optional<Category> loadedCategory = categoryRepository.findById(id);
            model = loadedCategory.orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"));
        } else {
            model = new Category();
        }
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setColor(new Color(red, green, blue));
        return categoryRepository.saveAndFlush(model).getId().toString();
    }
    
    public void deleteCategory(String id) {
        UUID uuid = UUID.fromString(id);
        resetDependentExpenses(uuid);
        categoryRepository.deleteById(uuid);
    }
    
    private void resetDependentExpenses(UUID categoryID) {
        ExpenseFilter filter = new ExpenseFilter();
        filter.getCategoryIdentifiers().add(categoryID);
        List<Expense> list = expenseRepository.find(filter);
        list.forEach(e -> e.setCategory(null));
        expenseRepository.saveAll(list);
        expenseRepository.flush();
    }
}
