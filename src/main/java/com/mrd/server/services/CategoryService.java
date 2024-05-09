package com.mrd.server.services;

import com.mrd.server.dto.CategoryDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto)throws IOException;

    List<CategoryDto> getAllCategories();
    void deleteCategory(Long id);
    CategoryDto updateCategory(CategoryDto categoryDto) throws IOException;

    List<CategoryDto> getCategoriesByName(String name);
}
