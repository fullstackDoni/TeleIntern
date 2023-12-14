package kz.internjava.TeleIntern.service;

import kz.internjava.TeleIntern.bot.TelegramBot;
import kz.internjava.TeleIntern.dto.CategoryDTO;
import kz.internjava.TeleIntern.mapper.CategoryMapper;
import kz.internjava.TeleIntern.model.Category;
import kz.internjava.TeleIntern.repository.CategoryRepository;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::categoryDTO)
                .collect(Collectors.toList());
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

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? CategoryMapper.categoryDTO(category) : null;
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentId()).orElse(null);
            category.setParent(parentCategory);
        }

        categoryRepository.save(category);
    }

    public void removeCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
