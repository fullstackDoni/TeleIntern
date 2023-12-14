package kz.internjava.TeleIntern.mapper;

import kz.internjava.TeleIntern.dto.CategoryDTO;
import kz.internjava.TeleIntern.model.Category;

public class CategoryMapper {
    public static CategoryDTO categoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(categoryDTO.getName());
        if (category.getParent() != null) {
            categoryDTO.setParentId(categoryDTO.getParentId());
        }
        return categoryDTO;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(category.getName());
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = new Category();
            parentCategory.setId(categoryDTO.getParentId());
            category.setParent(parentCategory);
        }

        return category;
    }
}
