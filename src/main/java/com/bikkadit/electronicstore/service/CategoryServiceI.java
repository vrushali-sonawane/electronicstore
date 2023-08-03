package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.help.PageableResponse;

public interface CategoryServiceI {


    //create
    CategoryDto  createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    //delete
    void deleteCategory(String categoryId);

    //get all
    PageableResponse<CategoryDto>  getAllCategories(int pageNUmber,int pageSize,String sortBy,String sortDir);

    //get single category
    CategoryDto getCategory(String categoryId);

    //search
}
