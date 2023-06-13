package com.example.RegAndLoginApi.Controller;

import com.example.RegAndLoginApi.Entity.Role;
import com.example.RegAndLoginApi.Entity.User;
import com.example.RegAndLoginApi.Service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    void registerHere() throws Exception {
        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .enabled(false)
                .build();

        String json=new ObjectMapper().writeValueAsString(user);

        when(userService.registerHere(any(User.class))).thenReturn("Registration successful");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registration successful"))
                .andDo(print());

    }
    @Test
    void login() {

    }

    @Test
    void resendToken() {
    }

    @Test
    void getToken() {
    }

    @Test
    void sendFpToken() {
    }

    @Test
    void getFpToken() {
    }
}