package dev.ahmed.blog.services.impl;

import dev.ahmed.blog.domain.UpdateCategoryRequest;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.repositories.CategoryRepository;
import dev.ahmed.blog.services.CategoryServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServices {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists with name " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElse(null);

        if (category != null) {
            if (!category.getPosts().isEmpty()) {
                throw new IllegalStateException("Category has posts associated with it");
            }
            categoryRepository.delete(category);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Category updateCategory(UUID id, UpdateCategoryRequest updateCategoryRequest) {
        var existingCategory = getCategoryById(id);
        existingCategory.setName(updateCategoryRequest.name());
        return categoryRepository.save(existingCategory);
    }
}