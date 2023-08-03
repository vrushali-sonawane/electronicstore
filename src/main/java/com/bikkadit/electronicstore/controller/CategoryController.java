package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.help.ApiResponseMessage;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.help.ImageResponse;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.service.CategoryServiceI;
import com.bikkadit.electronicstore.service.FileServiceI;
import com.bikkadit.electronicstore.service.ProductServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceI categoryServiceI;
    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${category.image.path}")
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);
    //create

    /**
     * @author Ashvini sonawane
     * @apinote create category
     * @param categoryDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Initiating request for create category");
      //call service to save object
        CategoryDto categoryDto1 = categoryServiceI.createCategory(categoryDto);
        logger.info("Completed request for create category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }
    //update

    /**
     * @author Ashvini sonawane
     * @apiNote update category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
      logger.info("Initiating request to update Category:{}",categoryId);
        CategoryDto updatedCategory = categoryServiceI.updateCategory(categoryDto, categoryId);
        logger.info("Completed request to update Category:{}",categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    //delete

    /**
     * @author Ashvini Sonawane
     * @apiNote delete category
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId){
        logger.info("Initiating request for deleting category:{}",categoryId);
        categoryServiceI.deleteCategory(categoryId);

        logger.info("Completing request for deleting category:{}",categoryId);
        return new ResponseEntity<>(AppConstant.DELETE_CATEGORY,HttpStatus.OK);
    }
    //getAll categories

    /**
     * @author Ashvini sonawane
     * @apiNote get all category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue =AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY_TITLE,required = false)String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
    ){
        logger.info("Initiating request for getting all categories");
        PageableResponse<CategoryDto> pageableResponse = categoryServiceI.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request for getting all categories");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single category

    /**
     * @author Ashvini sonawane
     * @apiNote get single category
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        logger.info("Initiating request to getting single category:{}",categoryId);
        CategoryDto categoryDto = categoryServiceI.getCategory(categoryId);
        logger.info("Complete request to getting single category:{}",categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    //upload image for category

    /**
     * @author Ashvini sonawane
     * @apiNote upload category image
     * @param image
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile image,@PathVariable String categoryId) throws IOException {
       logger.info("Initiating request for upload category image:{}",categoryId);
        String imageName = fileServiceI.uploadFile(image, imageUploadPath);
        CategoryDto category = categoryServiceI.getCategory(categoryId);
        category.setCoverImage(imageName);
        CategoryDto update = categoryServiceI.updateCategory(category, categoryId);

        ImageResponse response=ImageResponse.builder()
                .imageName(imageName).message("Image is uploaded successfully").success(true).status(HttpStatus.CREATED).build();

        logger.info("Completed request for upload category image:{}",categoryId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);


    }
    //serve image

    /**
     * @author Ashvini sonawane
     * @apiNote serve image
     * @param categoryId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{categoryId}")
    public void serveImage(@PathVariable String categoryId , HttpServletResponse response) throws IOException {
        logger.info("Initiating request for serve image:"+categoryId);
        CategoryDto category = categoryServiceI.get(categoryId);
        logger.info("category cover image name: {}",category.getCoverImage());
        InputStream resource = fileServiceI.getResource(imageUploadPath, category.getCoverImage());
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Complete request for serve image:"+categoryId);
    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @RequestBody ProductDto productDto,
            @PathVariable String categoryId
    ){
        logger.info("Initiating request to create product with category :{}",categoryId);
        ProductDto productWithCategory = productServiceI.createWithCategory(productDto, categoryId);
        logger.info("Initiating request to create product with category :{}",categoryId);
       return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }

    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String productId, @PathVariable String categoryId
    ){
        ProductDto productDto = productServiceI.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);

    }
    //get products of categories
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value="pageSize",defaultValue =AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstant.SORT_BY_TITLE,required = false)String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir
    ){
        PageableResponse<ProductDto> response = productServiceI.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
