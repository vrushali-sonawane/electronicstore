package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.help.PageableResponse;

public interface CategoryServiceI {


    //create
    CategoryDto  create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto>  getAll(int pageNUmber,int pageSize,String sortBy,String sortDir);

    //get single category
    CategoryDto get(String categoryId);

    //search
}
