package com.mrd.server.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mrd.server.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB",length = 1000000000)
    private byte[] image;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Course> courses;

    public CategoryDto getDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);
        categoryDto.setDescription(description);
        categoryDto.setImage(image);
        return categoryDto;
    }
}
