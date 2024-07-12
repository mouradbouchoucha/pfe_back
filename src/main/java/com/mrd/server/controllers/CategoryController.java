package com.mrd.server.controllers;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.models.Category;
import com.mrd.server.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin("*")

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile    ) throws IOException {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        if (imageFile != null) {
            byte[] imageData = imageFile.getBytes();
            categoryDto.setImage(imageData);
        }
        categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/all_SortedByCreatedAt")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesSortedByCreatedAt() {
        List<CategoryDto> categories = categoryService.getAllCategoriesOrderByCreatedAt();
        return ResponseEntity.ok().body(categories);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        categoryDto.setImageFile(imageFile);

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByName(@PathVariable String name) {
        List<CategoryDto> categoriesDtos = categoryService.getCategoriesByName(name);
        return ResponseEntity.ok().body(categoriesDtos);
    }
}
