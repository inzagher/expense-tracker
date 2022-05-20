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
@Tag(name = "Category operations")
public class CategoryApiController {
    private final CategoryService service;

    @GetMapping(path = "/api/categories")
    @Operation(summary = "Find all categories")
    public List<CategoryDTO> findAll() {
        return service.findAllCategories();
    }

    @GetMapping(path = "/api/categories/{id}")
    @Operation(summary = "Get category by id")
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PostMapping(path = "/api/categories")
    @Operation(summary = "Create category")
    public void create(@RequestBody CategoryDTO dto) {
        service.createCategory(dto);
    }

    @PutMapping(path = "/api/categories")
    @Operation(summary = "Edit category")
    public void edit(@RequestBody CategoryDTO dto) {
        service.editCategory(dto);
    }

    @DeleteMapping(path = "/api/categories/{id}")
    @Operation(summary = "Delete category by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteCategoryById(id);
    }
}
