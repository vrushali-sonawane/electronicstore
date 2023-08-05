package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.model.Product;
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

import java.util.Arrays;
import java.util.Date;
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
        Mockito.when(categoryServiceI.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void deleteCategoryTestTest() throws Exception {
        String string = UUID.randomUUID().toString();

       mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+string)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

}

    @Test
    void getAllCategoriesTest() throws Exception {
    CategoryDto categoryDto=CategoryDto.builder().title("mobiles").description("different types of mobile").coverImage("abc.png").build();
    CategoryDto categoryDto1=CategoryDto.builder().title("laptops").description("each variety of laptops have 10% off").coverImage("xyz.png").build();
    CategoryDto categoryDto2=CategoryDto.builder().title("chargers").description("as per customer requirement ").coverImage("abc.png").build();

        PageableResponse pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(categoryDto2,categoryDto1,categoryDto));
        pageableResponse.setPageNumber(10);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalElements(100);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setLastPage(false);

        Mockito.when(categoryServiceI.getAllCategories(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getSingleCategoryTest() throws Exception {
        String string = UUID.randomUUID().toString();
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryServiceI.getCategory(string)).thenReturn(categoryDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+string)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void uploadCategoryImageTest() {
    }

    @Test
    void serveImageTest() {
    }

    @Test
    void createProductWithCategoryTest() throws Exception {
        String categoryId=UUID.randomUUID().toString();

        String productId= UUID.randomUUID().toString();
        Product   product1 = Product.builder()
                .productId(productId)
                .title("Samsung A34")
                .discountedPrice(3000.00)
                .price(20000.00)
                .quantity(100)
                .live(true)
                .addedDate(new Date())
                .stock(true)
                .description("This mobile  has many fetures")
                .productImage("abc.png")
                .category(category)
                .build();

        ProductDto productDto = mapper.map(product1, ProductDto.class);

        Mockito.when(productServiceI.createWithCategory(Mockito.any(),Mockito.anyString())).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/"+categoryId+"/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

}

    @Test
    void updateCategoryOfProductTest() {
    }

    @Test
    void getProductOfCategoryTest() {
    }
}