package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.model.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDto createUser(UserDto userDto) {
        //generate unique id in String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        logger.info("Initiating dao call to create user");
     //   User user=dtoToEntity(userDto);
       User user= modelMapper.map(userDto,User.class);
        User savedUser=userRepository.save(user);
      // UserDto userDto1= EntityToDto(savedUser);
        UserDto userDto1=modelMapper.map(savedUser,UserDto.class);
        logger.info("Completed dao call to create user");
        return userDto1;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
    logger.info("Initiating dao call to update user:"+userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId"));
       user.setName(userDto.getName());
       user.setPassword(userDto.getPassword());
       user.setAbout(userDto.getAbout());
       user.setGender(userDto.getGender());
       user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(user, UserDto.class);

        logger.info("Completed  dao call to update user:"+userId);

        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating dao call to delete user :"+userId);
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId"));

      userRepository.delete(user1);
        logger.info("Completed dao call to delete user :"+userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Initiating dao call to get all users");

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        logger.info("Completed dao call to get all users");

        return userDtos;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating Dao call to getting single user:"+userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        logger.info("Completed Dao call to getting single user :"+userId);
        return userDto;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        logger.info("Initiating dao call to get user by email:"+email);
        User user= userRepository.findUserByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        logger.info("Completed dao call to get user by email:"+email);
        return userDto;
    }


    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao call for searching users:"+keyword);
       List<User> users= userRepository.findUserByNameContaining(keyword);
        List<UserDto> userDtos = users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        logger.info("Completed dao call for searching users:"+keyword);

        return userDtos;
    }


    private User dtoToEntity(UserDto userDto) {

        /*UserDto.builder()
                .userId(userDto.getUserId())
                .name(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName()).build();*/
        return modelMapper.map(userDto,User.class);
    }

    private UserDto EntityToDto(User savedUser) {
        /*User.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .gender(savedUser.getGender())
                .about(savedUser.getAbout())
                .imageName(savedUser.getImageName()).build();*/
        return modelMapper.map(savedUser,UserDto.class);
    }
}
