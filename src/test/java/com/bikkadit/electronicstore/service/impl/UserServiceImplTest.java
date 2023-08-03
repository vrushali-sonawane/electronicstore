package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.model.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
   @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ModelMapper mapper;

    User user;

    User user1;

    List<User> users;
    UserDto userDto;
    @BeforeEach
    public void init(){
        String id1 = UUID.randomUUID().toString();
        user = User.builder()
                .userId(id1)
                .name("ashvini sonawane")
                .email("ashusonawane123@gmail.com")
                .password("34ashvinis")
                .about("java developer")
                .gender("female")
                .imageName("abc.png")
                .build();

        String id2 = UUID.randomUUID().toString();
        user1 = User.builder()
                .userId(id2)
                .name("vrushali sonawane")
                .email("vrushu@gmail.com")
                .password("7Vrushalis")
                .about("java developer")
                .gender("female")
                .imageName("xyz.png")
                .build();

        users= new ArrayList<>();
        users.add(user);
        users.add(user1);

    }

    @Test
    void createUser() {
         Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user2 = userServiceImpl.createUser(mapper.map(user, UserDto.class));

        Assertions.assertEquals("ashvini sonawane",user2.getName());
    }

    @Test
    void updateUser() {
        String id = UUID.randomUUID().toString();
        userDto = UserDto.builder()
                .userId(id)
                .name("vrushali sonawane")
                .email("vrushali13@gmail.com")
                .password("23Vrushali")
                .about("Java developer")
                .gender("female")
                .imageName("abc.png")
                .build();
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto userDto1 = userServiceImpl.updateUser(userDto, id);

        Assertions.assertEquals("23Vrushali",user.getPassword());

    }

    @Test
    void deleteUser() {
        String id1 = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(id1)).thenReturn(Optional.of(user));
        userServiceImpl.deleteUser(id1);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }

    @Test
    void getAllUsers() {

    }

    @Test
    void getUserById() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void searchUser() {
    }
}