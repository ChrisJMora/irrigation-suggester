//package com.example.demo.tasks;
//
//import com.example.demo.infraestructure.config.security.SecurityConfig;
//import com.example.demo.infraestructure.config.security.admin.JwtUtil;
//import com.example.demo.infraestructure.config.security.admin.LoginRequest;
//import com.example.demo.infraestructure.config.security.admin.CustomUserDetailsService;
//import com.example.demo.interfaces.controllers.AuthController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(AuthController.class)
//@Import(SecurityConfig.class)
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @MockBean
//    private CustomUserDetailsService userDetailsService;
//
//    @Test
//    public void testAuthenticate_Success() throws Exception {
//        // Mocking behavior of userDetailsService
//        String username = "admin1";
//        String password = "password1";
//        UserDetails userDetails = User.builder()
//                .username(username)
//                .password(password)
//                .roles("ADMINISTRATOR")
//                .build();
//
//        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//        when(jwtUtil.generateToken(userDetails)).thenReturn("mock-jwt-token");
//
//        LoginRequest request = new LoginRequest();
//        request.setUsername(username);
//        request.setPassword(password);
//
//        mockMvc.perform(post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"" + username + "\"," +
//                                " \"password\":\"" + password + "\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
//
//        verify(authenticationManager, times(1)).authenticate(any());
//        verify(userDetailsService, times(1)).loadUserByUsername(username);
//        verify(jwtUtil, times(1)).generateToken(userDetails);
//    }
//}
