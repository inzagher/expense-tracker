package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryApiController {
    private final CategoryService categoryService;
    
    @Autowired
    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping(path = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDTO> list() {
        return categoryService.getAllCategories();
    }
    
    @GetMapping(path = "/api/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDTO read(@PathVariable String id) {
        return categoryService.getCategoryById(id).orElse(null);
    }

    @PostMapping(path = "/api/categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody CategoryDTO category) {
        categoryService.storeCategory(category);
    }
    
    @DeleteMapping(path = "/api/categories/{id}")
    public void delete(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }
}
