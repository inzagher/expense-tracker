package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.CategoryDTO;
import inzagher.expense.tracker.server.model.Category;
import inzagher.expense.tracker.server.model.Color;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import java.util.Optional;
import java.util.UUID;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public Optional<CategoryDTO> getCategory(String id) {
        UUID uuid = UUID.fromString(id);
        return categoryRepository.findById(uuid).map(Category::toDTO);
    }
    
    public String storeCategory(CategoryDTO dto) {
        byte red = dto.getColor().getRed();
        byte green = dto.getColor().getGreen();
        byte blue = dto.getColor().getBlue();
        Category model = dto.getId() != null
                ? categoryRepository.getOne(UUID.fromString(dto.getId()))
                : new Category();
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setColor(new Color(red, green, blue));
        return categoryRepository.saveAndFlush(model).getId().toString();
    }
    
    public void deleteCategory(String id) {
        UUID uuid = UUID.fromString(id);
        categoryRepository.deleteById(uuid);
    }
}
