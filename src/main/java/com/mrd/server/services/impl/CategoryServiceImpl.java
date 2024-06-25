package com.mrd.server.services.impl;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.models.Category;
import com.mrd.server.repositories.CategoryRepository;
import com.mrd.server.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto)throws IOException {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        byte[] imageData = categoryDto.getImageFile().getBytes();
        category.setImage(imageData);

        return categoryRepository.save(category).getDto();
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::getDto).collect(Collectors.toList());
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) throws IOException {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow();

        // Update category properties
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // Check if image file is provided
        if (categoryDto.getImageFile() != null) {
            byte[] imageData = categoryDto.getImageFile().getBytes();
            category.setImage(imageData);
        }

        // Save the updated category
        categoryRepository.save(category);

        // Return updated DTO
        return category.getDto();
    }

    public List<CategoryDto> getCategoriesByName(String name) {
        if(name == null || name.isEmpty()) {
            return getAllCategories();
        }
        List<Category> categories = categoryRepository.findAllByNameContainingIgnoreCase(name);
        return categories.stream().map(Category::getDto).collect(Collectors.toList());
    }
}
