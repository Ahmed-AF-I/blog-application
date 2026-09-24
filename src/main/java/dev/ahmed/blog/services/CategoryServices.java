package dev.ahmed.blog.services;

import dev.ahmed.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryServices {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
}
