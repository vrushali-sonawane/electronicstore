package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.model.User;

import java.util.List;

public interface UserServiceI {

    //create
    UserDto createUser(UserDto userDto);
    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
     void deleteUser(String userId);

     //get all users
    List<UserDto> getAllUsers();

    //get single user by id
    UserDto getUserById(String userId);


    // get single user by email
    UserDto findUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    //other user specific feature

}
