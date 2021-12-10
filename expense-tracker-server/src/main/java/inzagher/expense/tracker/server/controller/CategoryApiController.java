package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.mapper.CategoryMapper;
import inzagher.expense.tracker.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping(path = "/api/categories")
    public List<CategoryDTO> findAll() {
        return service.findAllCategories();
    }

    @GetMapping(path = "/api/categories/{id}")
    public CategoryDTO getById(@PathVariable Integer id) {
        return service.getCategoryById(id);
    }

    @PostMapping(path = "/api/categories")
    public void create(@RequestBody CategoryDTO category) {
        service.createCategory(mapper.toCreateCommand(category));
    }

    @PutMapping(path = "/api/categories")
    public void edit(@RequestBody CategoryDTO category) {
        service.editCategory(mapper.toEditCommand(category));
    }

    @DeleteMapping(path = "/api/categories/{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteCategoryById(id);
    }
}
