package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.service.CategoryServiceI;
import com.bikkadit.electronicstore.service.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @MockBean
    private CategoryServiceI categoryServiceI;
    @MockBean
    private ProductServiceI productServiceI;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;
    private Category category;
@BeforeEach
void init(){
    String categoryId = UUID.randomUUID().toString();
    category=Category.builder()
            .categoryId(categoryId)
            .title("ear phones")
            .description("available with 10% discount")
            .coverImage("abc.png")
            .build();

}

    @Test
    void createCategoryTest() throws Exception {

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryServiceI.createCategory(Mockito.any())).thenReturn(categoryDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());


    }
    private String convertObjectToJsonString(Object category){
        try{
            return new ObjectMapper().writeValueAsString(category);
        }catch(Exception e){
            e.printStackTrace();

        }
        return null;

    }

    @Test
    void updateCategoryTest() throws Exception {
        String id = UUID.randomUUID().toString();
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryServiceI.getCategory(id)).thenReturn(categoryDto);
        Mockito.when(categoryServiceI.updateCategory(categoryDto,id)).thenReturn(categoryDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void deleteCategoryTestTest() {
    }

    @Test
    void getAllCategoriesTest() {
    }

    @Test
    void getSingleCategoryTest() {
    }

    @Test
    void uploadCategoryImageTest() {
    }

    @Test
    void serveImageTest() {
    }

    @Test
    void createProductWithCategoryTest() {
    }

    @Test
    void updateCategoryOfProductTest() {
    }

    @Test
    void getProductOfCategoryTest() {
    }
}