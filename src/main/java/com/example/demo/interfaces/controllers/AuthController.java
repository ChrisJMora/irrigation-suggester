//package com.example.demo.interfaces.controllers;
//
//import com.example.demo.infraestructure.config.security.admin.CustomUserDetailsService;
//import com.example.demo.infraestructure.config.security.admin.JwtUtil;
//import com.example.demo.infraestructure.config.security.admin.LoginRequest;
//import com.example.demo.infraestructure.config.security.admin.LoginResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/login")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService userDetailsService;
//
//    public AuthController(AuthenticationManager authenticationManager,
//                          JwtUtil jwtUtil,
//                          CustomUserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @PostMapping()
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//            var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//            String jwt = jwtUtil.generateToken(userDetails);
//            return ResponseEntity.ok(new LoginResponse(jwt));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).build();
//        }
//    }
//}
