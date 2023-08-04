package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.CategoryDto;
import org.springframework.boot.test.context.SpringBootTest;
import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    @Autowired
    private ModelMapper mapper;

    Category category;

    CategoryDto categoryDto;

    List<Category> categories;

    Category category1;
    @BeforeEach
    void init(){
        String id = UUID.randomUUID().toString();
        category=Category.builder()
                .title("Tv sets")
                .description("different types of tv available")
                .coverImage("abc.png")
                .build();

    }


    @Test
    void createCategory() {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto1 = categoryServiceImpl.createCategory(mapper.map(category, CategoryDto.class));
        Assertions.assertEquals("Tv sets",categoryDto1.getTitle());
    }

    @Test
    void updateCategory() {
        String id1 = UUID.randomUUID().toString();
        categoryDto=CategoryDto.builder()
                .categoryId(id1)
                .title("iPhone")
                .description(" All this brands Dicount upto 10%")
                .coverImage("xyz.png")
                .build();

        Mockito.when(categoryRepository.findById(id1)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto updatedCategory = categoryServiceImpl.updateCategory(categoryDto, id1);
        Assertions.assertNotNull(updatedCategory);
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getCategory() {
    }
}