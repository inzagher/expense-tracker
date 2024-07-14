package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Category operations")
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    @Operation(summary = "Find all categories")
    public List<CategoryDTO> findAll() {
        return service.findAllCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PostMapping
    @Operation(summary = "Create category")
    public void create(@RequestBody CategoryDTO dto) {
        service.createCategory(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit category")
    public void edit(@PathVariable Long id,
                     @RequestBody CategoryDTO dto) {
        service.editCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteCategoryById(id);
    }
}
