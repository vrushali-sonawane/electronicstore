package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.help.ApiResponseMessage;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.help.ImageResponse;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.model.Product;
import com.bikkadit.electronicstore.service.FileServiceI;
import com.bikkadit.electronicstore.service.ProductServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${product.image.path}")
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(ProductController.class);


    //create

    /**
     * @author Ashvini sonawane
     * @apiNote create product
     * @param productDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        logger.info("Initiating request to create product:");
        ProductDto createdProduct = productServiceI.createProduct(productDto);
        logger.info("Complete request to create product:");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    //update
    /**
     * @author Ashvini sonawane
     * @apiNote update product
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId){
        logger.info("Initiating request to update product:{}",productId);
        ProductDto updatedProduct = productServiceI.updateProduct(productDto, productId);
        logger.info("Complete request to update product:{}",productId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }
    //delete
    /**
     * @author Ashvini sonawane
     * @apiNote delete product
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        logger.info("Initiating request to delete product:{}",productId);
        productServiceI.deleteProduct(productId);
        logger.info("Complete request to update product:{}",productId);
        return new ResponseEntity<>(AppConstant.DELETE_PRODUCT, HttpStatus.OK);
    }
    //get single product
    /**
     * @author Ashvini sonawane
     * @apiNote get single product
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        logger.info("Initiating request to getting single product:{}",productId);
        ProductDto singleProduct = productServiceI.getSingleProduct(productId);
        logger.info("Complete request to get single product:{}",productId);
        return new ResponseEntity<>(singleProduct,HttpStatus.FOUND);

    }
    //get all product
    /**
     * @author Ashvini sonawane
     * @apiNote get all product
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstant.SORT_DIR,required = false)String sortDir
    ){
        logger.info("Initiating request to get all product:");
        PageableResponse<ProductDto> allProduct = productServiceI.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete request to get all product:");
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    //get all live product
    /**
     * @author Ashvini sonawane
     * @apiNote get all live product
     */
    @GetMapping("live/")
    public ResponseEntity<PageableResponse> getAllLiveProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstant.SORT_DIR,required = false)String sortDir
    ){
        logger.info("Initiating request to get all live product:");
        PageableResponse<ProductDto> allLiveProduct = productServiceI.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete request to get all live product:");
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);
    }

    //search product
    /**
     * @author  Ashvini sonawane
     * @apiNote search product
     */
    @GetMapping("search/{subTitle}")
    public ResponseEntity<PageableResponse> searchProduct(
            @PathVariable String subTitle,
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstant.SORT_DIR,required = false)String sortDir
    ){
        logger.info("Initiating request to search product:{}",subTitle);
        PageableResponse<ProductDto> response = productServiceI.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete request to search product:{}",subTitle);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // upload product image
    /**
     *
     */
    @PostMapping("image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam ("productImage")MultipartFile image,@PathVariable String productId) throws IOException {
        String imageName = fileServiceI.uploadFile(image, imageUploadPath);
        logger.info("Initiating request to upload product image :{}",productId);
        ProductDto product = productServiceI.getSingleProduct(productId);
        product.setProductImage(imageName);
        ProductDto updatedProduct = productServiceI.updateProduct(product, productId);
        ImageResponse response=ImageResponse.builder()
                .imageName(imageName).message("Image is uploaded successfully").success(true).status(HttpStatus.CREATED).build();
        logger.info("Complete request to upload product image:{}",productId);

        return new ResponseEntity<>(response,HttpStatus.CREATED);



    }
}
