package dev.ahmed.blog.controllers;

import dev.ahmed.blog.domain.dtos.CategoryDto;
import dev.ahmed.blog.domain.dtos.CreateCategoryRequest;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.mappers.CategoryMapper;
import dev.ahmed.blog.services.CategoryServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServices categoryServices;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<CategoryDto> categories = categoryServices.listCategories().stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(
            @Valid @RequestBody
            CreateCategoryRequest createCategoryRequest
    ) {
        Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryServices.createCategory(categoryToCreate);
        return new ResponseEntity<>(
                categoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable UUID id
    ) {
        categoryServices.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
