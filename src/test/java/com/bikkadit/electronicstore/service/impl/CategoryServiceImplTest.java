package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
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
    void create() {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto1 = categoryServiceImpl.create(mapper.map(category, CategoryDto.class));
        Assertions.assertEquals("Tv sets",categoryDto1.getTitle());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }
}