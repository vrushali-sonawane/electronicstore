package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.model.User;
import com.bikkadit.electronicstore.service.FileServiceI;
import com.bikkadit.electronicstore.service.UserServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserServiceI userServiceI;
    @MockBean
    private FileServiceI fileServiceI;
    @Autowired
    private  UserController userController;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    User user;
@BeforeEach
void init(){
    String userId = UUID.randomUUID().toString();
    user=User.builder()
            .userId("userId")
            .name("ashvini sonawane")
            .email("ashvini156@gmail.com")
            .password("82Ashvini")
            .about("java developer")
            .gender("female")
            .imageName("abc.png")
            .build();

}

    @Test
    void createUserTest() throws Exception {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }
    private String convertObjectToJsonString(Object user){
    try{
        return new ObjectMapper().writeValueAsString(user);
    }catch(Exception e){
    e.printStackTrace();

    }
    return null;

    }

    @Test
    void updateUserTest() throws Exception {
        String userId = UUID.randomUUID().toString();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/"+ userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    void deleteUserTest() throws Exception {
        String userId = UUID.randomUUID().toString();
       mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+userId)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());



    }

    @Test
    void getAllUsersTest() {
    }

    @Test
    void getSingleUserTest() {
    }

    @Test
    void getSingleUserByEmailTest() {
    }

    @Test
    void searchUserTest() {
    }

    @Test
    void uploadUserImageTest() {
    }

    @Test
    void serveImageTest() {
    }
}