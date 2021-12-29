package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.command.CreateCategoryCommand;
import inzagher.expense.tracker.server.command.EditCategoryCommand;
import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.exception.NotFoundException;
import inzagher.expense.tracker.server.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.*;
import inzagher.expense.tracker.server.query.ExpenseQueryFilter;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public List<CategoryDTO> findAllCategories() {
        log.info("Find all categories");
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Transactional
    public CategoryDTO getCategoryById(@NonNull Integer id) {
        log.info("Find category with id {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Integer createCategory(@NonNull CreateCategoryCommand command) {
        log.info("Create category. Command: {}", command);
        var entity = new Category(command);
        return categoryRepository.save(entity).getId();
    }

    @Transactional
    public void editCategory(@NonNull EditCategoryCommand command) {
        log.info("Edit category. Command: {}", command);
        var entity = categoryRepository
                .findById(command.getId())
                .orElseThrow(NotFoundException::new);
        entity.edit(command);
        categoryRepository.save(entity);
    }

    @Transactional
    public void deleteCategoryById(@NonNull Integer id) {
        log.info("Delete category with id {}", id);
        if (isAnyExpensePresent(id)) {
            var message = String.format("Category with id %d has expenses", id);
            throw new ExpenseTrackerException(message);
        }
        categoryRepository.deleteById(id);
    }

    private boolean isAnyExpensePresent(Integer categoryId) {
        log.info("Find expenses with category id {}", categoryId);
        var filter = new ExpenseQueryFilter();
        filter.getCategoryIdentifiers().add(categoryId);
        return !expenseRepository.find(filter).isEmpty();
    }
}
