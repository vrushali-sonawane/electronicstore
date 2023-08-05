package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.help.PageableResponse;
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

import java.util.Arrays;
import java.util.List;
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

    List<UserDto> userDtos;
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
    void getAllUsersTest() throws Exception {
    UserDto userDto=UserDto.builder().name("vrushali").email("vrushali@gmail.com").password("45@Vrushali").about("java developer").gender("female").imageName("abc.png").build();

    UserDto userDto1=UserDto.builder().name("samir").email("samir23@gmail.com").password("34Samirxyz").about("tester").gender("male").imageName("xyz.png").build();

    UserDto userDto2=UserDto.builder().name("sagar").email("sager68@gmail.com").password("10Sagarrs").about("java developer").imageName("abc.png").gender("male").build();

        PageableResponse pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(Arrays.asList(userDto,userDto1,userDto2));
        pageableResponse.setPageNumber(10);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalElements(100);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setLastPage(false);

        Mockito.when(userServiceI.getAllUsers(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getSingleUserTest() throws Exception {
        String userId=UUID.randomUUID().toString();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.getUserById(userId)).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void getSingleUserByEmailTest() throws Exception {
        String string = UUID.randomUUID().toString();
        user =User.builder().email("vrushali123@gmail.com").build();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userServiceI.findUserByEmail(userDto.getEmail())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());



    }

    @Test
    void searchUserTest() throws Exception {
        String userId = UUID.randomUUID().toString();
        UserDto userDto=UserDto.builder().name("sanket").email("sanket23@gmail.com").password("45Sanketss").gender("male").about("java developer").imageName("abc.png").build();
        UserDto userDto1=UserDto.builder().name("shekhar").password("11Sheksarss").email("shekhar122@gmail.com").gender("male").about("tester").imageName("xyz.png").build();
        UserDto userDto2=UserDto.builder().name("sarite").password("44Sarita").email("sarita11@gmail.com").gender("female").about("tester").imageName("xyz.png").build();
        List<UserDto> list = Arrays.asList(userDto, userDto1, userDto2);


        Mockito.when(userServiceI.searchUser(Mockito.anyString())).thenReturn(list);
       mockMvc.perform(MockMvcRequestBuilders.get("/users/search/")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    void uploadUserImageTest() {
    }

    @Test
    void serveImageTest() {
    }
}