package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
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
    public CategoryDTO getById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @PostMapping(path = "/api/categories")
    public void create(@RequestBody CategoryDTO dto) {
        service.createCategory(dto);
    }

    @PutMapping(path = "/api/categories")
    public void edit(@RequestBody CategoryDTO dto) {
        service.editCategory(dto);
    }

    @DeleteMapping(path = "/api/categories/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteCategoryById(id);
    }
}
