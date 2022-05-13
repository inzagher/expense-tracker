package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.criteria.ExpenseSearchCriteria;
import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.specification.ExpenseSpecification;
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
    public CategoryDTO getCategoryById(@NonNull Long id) {
        log.info("Find category with id {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createCategory(@NonNull CategoryDTO dto) {
        log.info("Create category. Data: {}", dto);
        var entity = categoryMapper.toEntity(dto);
        return categoryRepository.save(entity).getId();
    }

    @Transactional
    public void editCategory(@NonNull CategoryDTO dto) {
        log.info("Edit category. Data: {}", dto);
        var entity = categoryRepository
                .findById(dto.getId())
                .orElseThrow(NotFoundException::new);
        categoryMapper.mergeToExistingEntity(entity, dto);
        categoryRepository.save(entity);
    }

    @Transactional
    public void deleteCategoryById(@NonNull Long id) {
        log.info("Delete category with id {}", id);
        if (isAnyCorrelatedExpensePresent(id)) {
            var message = String.format("Category with id %d has expenses", id);
            throw new ExpenseTrackerException(message);
        }
        categoryRepository.deleteById(id);
    }

    private boolean isAnyCorrelatedExpensePresent(Long categoryId) {
        log.info("Find expenses with category id {}", categoryId);
        var criteria = new ExpenseSearchCriteria();
        var specification = new ExpenseSpecification(criteria);
        criteria.getCategoryIdentifiers().add(categoryId);
        return !expenseRepository.findAll(specification).isEmpty();
    }
}
