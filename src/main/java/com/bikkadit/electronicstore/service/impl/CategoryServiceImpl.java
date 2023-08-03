package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.service.CategoryServiceI;
import com.bikkadit.electronicstore.utility.Helper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryServiceI {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.image.path}")
    private String imageUploadPath;


    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        //creating category id randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        logger.info("Initiating Dao call for create category");
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto categoryDto1 = modelMapper.map(savedCategory, CategoryDto.class);
        logger.info("Complete Dao call for create category");

        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating Dao call to Update Category:"+categoryId);

        //get category of given id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));

        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Completed Dao call to Update Category:"+categoryId);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating Dao Call to delete Category:{}",categoryId);
        //get category for given id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND));
        String fullPath = imageUploadPath+ category.getCoverImage();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch(NoSuchFileException ex){
            logger.info("category image not found in folder");
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        logger.info("Complete Dao Call to delete Category:{}",categoryId);
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNUmber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNUmber,pageSize,sort);
        logger.info("Initiating Dao call to get All categories:");
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        logger.info("Completed Dao call to get All categories:");
        return pageableResponse;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        logger.info("Initiating Dao call to get single category:{}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND ));
        logger.info("Completed Dao call to get single category:{}",categoryId);
        return modelMapper.map(category,CategoryDto.class);
    }
}
