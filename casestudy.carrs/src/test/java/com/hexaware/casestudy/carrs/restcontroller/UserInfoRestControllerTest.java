package com.hexaware.casestudy.carrs.restcontroller;



import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hexaware.casestudy.carrs.dto.UserInfoDTO;
import com.hexaware.casestudy.carrs.service.IUserInfoService;

@WebMvcTest(UserInfoRestController.class)
class UserInfoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserInfoService userService;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserInfoDTO> users = Arrays.asList(
            new UserInfoDTO(),
            new UserInfoDTO()
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(2)))
            .andExpect(jsonPath("$[0].userName", is("Mithra")))
            .andExpect(jsonPath("$[1].roles", is("ADMIN")));
    }

    @Test
    void testGetUserById() throws Exception {
        UserInfoDTO user = new UserInfoDTO();

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userName", is("Mithra")))
            .andExpect(jsonPath("$.email", is("mithra@example.com")))
            .andExpect(jsonPath("$.roles", is("USER")));
    }
}
