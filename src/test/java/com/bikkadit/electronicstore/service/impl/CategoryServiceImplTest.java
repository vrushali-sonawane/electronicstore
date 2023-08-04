package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.CategoryDto;

import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Category;
import org.springframework.boot.test.context.SpringBootTest;

import com.bikkadit.electronicstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
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
                .categoryId(id)
                .title("Tv sets")
                .description("different types of tv available")
                .coverImage("abc.png")
                .build();
        String id1 = UUID.randomUUID().toString();
        category1=Category.builder()
                .categoryId(id1)
                .title("mobile")
                .description("different types of mobile available")
                .coverImage("abc.png")
                .build();
        categories =new ArrayList<>();
        categories.add(category);
        categories.add(category1);


    }


    @Test
    void createCategoryTest() {
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto1 = categoryServiceImpl.createCategory(mapper.map(category, CategoryDto.class));
        Assertions.assertEquals("Tv sets",categoryDto1.getTitle());
    }

    @Test
    void updateCategoryTest() {
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
    void deleteCategoryTest() {
        String categoryId = UUID.randomUUID().toString();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        categoryServiceImpl.deleteCategory(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);


    }

    @Test
    void getAllCategories() {
        int pageNumber=0;
        int pageSize=2;
        String sortBy="name";
        String sortDir="asc";

        Sort sort= Sort.by("name").ascending();
        Page<Category> page=new PageImpl<>(categories);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(page);
        PageableResponse<CategoryDto> allCategories = categoryServiceImpl.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
          Assertions.assertEquals(2,allCategories.getContent().size());

    }

    @Test
    void getCategory() {
        String string = UUID.randomUUID().toString();
        Mockito.when(categoryRepository.findById(string)).thenReturn(Optional.of(category));
        CategoryDto category2 = categoryServiceImpl.getCategory(string);
        Assertions.assertEquals("Tv sets",category2.getTitle());
    }
}