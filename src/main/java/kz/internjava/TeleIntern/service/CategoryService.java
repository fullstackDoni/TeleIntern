package kz.internjava.TeleIntern.service;

import kz.internjava.TeleIntern.model.Category;
import kz.internjava.TeleIntern.repository.CategoryRepository;
import org.jvnet.hk2.annotations.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String name, Long parentId) {
        Category category = new Category();
        category.setName(name);

        if (parentId != null) {
            Optional<Category> parent = categoryRepository.findById(parentId);
            parent.ifPresent(category::setParent);
        }

        return categoryRepository.save(category);
    }

}
