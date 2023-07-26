package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Product;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.service.ProductServiceI;
import com.bikkadit.electronicstore.utility.Helper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service

public class ProductServiceImpl implements ProductServiceI {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    private Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        logger.info("Initiating dao call to create product");
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        ProductDto productDto1 = modelMapper.map(savedProduct, ProductDto.class);
        logger.info("Complete dao call to create product");
        return productDto1;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        logger.info("Initiating dao call to update product :{}",productId);
        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(product.isStock());
        Product updatedProduct = productRepository.save(product);

        //save the entity
        ProductDto updated = modelMapper.map(updatedProduct, ProductDto.class);
        logger.info("Complete dao call to update product :{}",productId);
        return updated;
    }

    @Override
    public void deleteProduct(String productId) {
        logger.info("Initiating dao call to delete product :{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        logger.info("Complete dao call to  delete date product :{}",productId);

    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating dao call to get all product");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("Complete dao call to get all product");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String sortBy,String sortDir){
        logger.info("Initiating dao call to getting all live product");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        logger.info("Complete dao call to getting all live product");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        logger.info("Initiating dao call to get single product:{}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        logger.info("Complete dao call to get single product:{}",productId);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
       logger.info("Initiating dao call to search product by title");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        logger.info("Completed dao call to search product by title");
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}
