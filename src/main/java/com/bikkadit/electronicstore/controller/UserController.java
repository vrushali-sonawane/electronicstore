package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.help.AppConstant;
import com.bikkadit.electronicstore.service.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

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
    public ResponseEntity<List<UserDto>> getAllUsers(){
        log.info("Initiating request to get all users");
        List<UserDto> all = userServiceI.getAllUsers();
        log.info("Completed request to get all users");
        return new ResponseEntity<List<UserDto>>( all,HttpStatus.OK);

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


}
