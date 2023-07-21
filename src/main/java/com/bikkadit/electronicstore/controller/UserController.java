package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.help.ImageResponse;
import com.bikkadit.electronicstore.help.PageableResponse;
import com.bikkadit.electronicstore.service.FileServiceI;
import com.bikkadit.electronicstore.service.UserServiceI;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
       @Autowired 
    private UserServiceI userServiceI;
    @Autowired
    private FileServiceI fileServiceI;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private static Logger log= LoggerFactory.getLogger(UserController.class);

    //create
    /**
     * @author Ashvini Sonawane
     * @apiNote create user
     * @param userDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        log.info("Initiating request to create user");
        UserDto createdUser = userServiceI.createUser(userDto);
        log.info("Completed request to create user");
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //update
    /**
     * @author Ashvini Sonawane
     * @apiNote
     * @param userDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            @Valid @PathVariable String userId
    ){
        log.info("Initiating request to update user:"+userId);
        UserDto userDto1 = userServiceI.updateUser(userDto, userId);
        log.info("Completed request to update user:"+userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    //delete
    /**
     * @author Ashvini Sonawane
     * @apiNote  delete user
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        log.info("Initiating request to delete user:"+userId);
        userServiceI.deleteUser(userId);
        log.info("Completed request to delete user:"+userId);
        return new ResponseEntity<>(AppConstant.DELETE_USER, HttpStatus.OK);
    }

    //get All users
    /**
     * @author Ashvini Sonawane
     * @api note get All user
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstant.SORT_DIR,required = false)String sortDir
    ){
        PageableResponse<UserDto> all = userServiceI.getAllUsers(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>( all,HttpStatus.OK);
    }

    //get user by id

    /**
     * @author Ashvini Sonawane
     * @apiNote get single user
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser( @PathVariable String userId){
        log.info("Initiating request to get single user:"+userId);
        UserDto userById = userServiceI.getUserById(userId);
        log.info("Completed request to get single user:"+userId);
        return  new ResponseEntity<>(userById,HttpStatus.OK);
    }

    // get user by email

    /**
     * @author Ashvini Sonawane
     * @apiNote get user by email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getSingleUserByEmail(@PathVariable String email){
        log.info("Initiating request to get single user by email:"+email);
        UserDto userByEmail = userServiceI.findUserByEmail(email);
        log.info("Completed request to get single user by email:"+email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);

    }
    //search user

    /**
     * @author Ashvini Sonawane
     * @apiNote search user
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        log.info("Initiating request to search user:"+keyword);
        List<UserDto> userDtos = userServiceI.searchUser(keyword);
        log.info("Completed request to search user:"+keyword);
        return new ResponseEntity<>(userDtos,HttpStatus.FOUND);
    }
    //upload user Image

    /**
     * @author Ashvini sonawane
     * @apiNote upload user image
     * @param image
     * @param userId
     * @return
     * @throws IOException
     */
    @PostMapping("image/{userId}")
    public ResponseEntity<ImageResponse > uploadUserImage(@RequestParam("userImage")MultipartFile image,
            @PathVariable String userId) throws IOException {
        log.info("Initiating request for upload user image:{}",userId);
        String imageName = fileServiceI.uploadFile(image, imageUploadPath);

        //save the image of particular user
        log.info(" request for getting user :{}",userId);
        UserDto user = userServiceI.getUserById(userId);
        user.setImageName(imageName);
        log.info("user found:");
        UserDto userDto = userServiceI.updateUser(user, userId);

        ImageResponse response=ImageResponse.builder()
                .imageName(imageName).message("Image is uploaded successfully").success(true).status(HttpStatus.CREATED).build();
        log.info("Completed request for uploading image:{}",userId);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
   //Serve user image
    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable String userId , HttpServletResponse response) throws IOException {
        UserDto user = userServiceI.getUserById(userId);
        log.info("User Image name : {}",user.getImageName());
        InputStream resource = fileServiceI.getResource(imageUploadPath, user.getImageName());
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
