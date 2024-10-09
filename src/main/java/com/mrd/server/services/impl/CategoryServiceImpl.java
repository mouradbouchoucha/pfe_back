package com.mrd.server.services.impl;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.dto.CourseDto;
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
        category.setImage(categoryDto.getImage());
        //System.out.println(categoryDto.getImageFile());
       // System.out.println(categoryDto.getImage() == null);

//        if (categoryDto.getImageFile() != null && !categoryDto.getImageFile().isEmpty()) {
//            category.setImage(categoryDto.getImageFile().getBytes());
//
//        }

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
        if (categoryDto.getImage() != null) {
            byte[] imageData = categoryDto.getImage()   ;
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

    @Override
    public List<CategoryDto> getAllCategoriesOrderByCreatedAt() {
        List<Category> categories = categoryRepository.findAllByOrderByCreatedAtDesc();
        return categories.stream().map(Category::getDto).collect(Collectors.toList());
    }

    public List<CategoryDto> getCategoriesWithCourses() {
        List<Category> categories = categoryRepository.findAll();

        // Convert Category entities to CategoryDto and include courses
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setImage(category.getImage());

        // Convert courses and set them in the CategoryDto
        if (category.getCourses() != null) {
            categoryDto.setCourses(
                    category.getCourses().stream().map(course -> {
                        CourseDto courseDto = new CourseDto();
                        courseDto.setId(course.getId());
                        courseDto.setName(course.getName());
                        courseDto.setDescription(course.getDescription());
                        courseDto.setStartDateTime(course.getStartDateTime());
                        courseDto.setImage(course.getImage());

                        return courseDto;
                    }).collect(Collectors.toList())
            );
        }

        return categoryDto;
    }
}
