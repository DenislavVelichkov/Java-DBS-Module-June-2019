package bookshop_system.services.impl;

import bookshop_system.models.Category;
import bookshop_system.repositories.CategoryRepository;
import bookshop_system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(String[] fileData) {
        for (String line : fileData) {
            Category category = new Category();
            category.setCategoryName(line);
            if (this.categoryRepository.findAllByCategoryName(line).isEmpty()) {
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }
}
