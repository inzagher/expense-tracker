package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.model.Expense;
import inzagher.expense.tracker.server.model.ExpenseFilter;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
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
public class CategoryService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .toList();
    }
    
    public Optional<CategoryDTO> getCategoryById(Integer id) {
        return categoryRepository.findById(id).map(categoryMapper::toDTO);
    }
    
    public Integer storeCategory(CategoryDTO dto) {
        Category model;
        Integer red = dto.getColor().getRed();
        Integer green = dto.getColor().getGreen();
        Integer blue = dto.getColor().getBlue();
        if (dto.getId() != null) {
            Optional<Category> loadedCategory = categoryRepository.findById(dto.getId());
            model = loadedCategory.orElseThrow(() -> new RuntimeException("CATEGORY NOT FOUND"));
        } else {
            model = new Category();
        }
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setColor(new Color(red, green, blue));
        model.setObsolete(dto.getObsolete());
        return categoryRepository.saveAndFlush(model).getId();
    }
    
    public void deleteCategory(Integer id) {
        resetDependentExpenses(id);
        categoryRepository.deleteById(id);
    }
    
    private void resetDependentExpenses(Integer categoryID) {
        ExpenseFilter filter = new ExpenseFilter();
        filter.getCategoryIdentifiers().add(categoryID);
        List<Expense> list = expenseRepository.find(filter);
        list.forEach(e -> e.setCategory(null));
        expenseRepository.saveAll(list);
        expenseRepository.flush();
    }
}
