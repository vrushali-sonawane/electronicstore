package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Product;

import java.util.List;

public interface ProductServiceI {
    //create
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProduct(ProductDto productDto,String productId);

    //delete
    void deleteProduct(String productId);

    //get all
   PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get All:Live
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get single product
    ProductDto getSingleProduct(String productId);

    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);


    //create product with category
   ProductDto createWithCategory(ProductDto productDto,String categoryId);
    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

    //
    PageableResponse<ProductDto>getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);






}
