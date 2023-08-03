package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.help.PageableResponse;
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
import org.springframework.data.domain.*;
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
                .email("vrushali123@gmail.com")
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
        int pageNumber=0;
        int pageSize=2;
        String sortBy="name";
        String sortDir="asc";

        Sort sort=Sort.by("name").ascending();
        Page<User> page=new PageImpl<>(users);
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Mockito.when(userRepository.findAll(pageable)).thenReturn(page);

        PageableResponse<UserDto> allUsers = userServiceImpl.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        Assertions.assertEquals(2,allUsers.getContent().size());

    }

    @Test
    void getUserById() {
        String stringId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(stringId)).thenReturn(Optional.of(user));
        UserDto userDto1 = userServiceImpl.getUserById(stringId);
        Assertions.assertEquals("ashvini sonawane",userDto1.getName());

    }

    @Test
    void findUserByEmail() {
        String email="vrushali123@gmail.com";
        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user1));
        UserDto userDto2 = userServiceImpl.findUserByEmail(email);
        Assertions.assertEquals("vrushali sonawane",userDto2.getName());

    }

    @Test
    void searchUser() {
        String keyword="sonawane";
        Mockito.when(userRepository.findUserByNameContaining(keyword)).thenReturn(users);
        List<UserDto> userDtos = userServiceImpl.searchUser(keyword);
        Assertions.assertEquals(2,userDtos.size());
    }
}