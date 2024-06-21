package com.devdoc.backend.controller;

import com.devdoc.backend.model.User;
import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")                 // /login, /logout 은 Spring-Security 기본 제공
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입 : Email 형식 검증
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

    // Resume 목록 조회 : User ID = 1 --------------------------------------------- Test Code
    @GetMapping("/resumes/test")
    public ResponseEntity<List<ResumeDTO>> getAllResumesByUserIdTest() {
        try {
            List<ResumeDTO> resumes = userService.getAllResumesByUserIdTest();
            return ResponseEntity.ok(resumes);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 목록 조회 : 로그인 User
    @GetMapping("/resumes")
    public ResponseEntity<List<ResumeDTO>> getAllResumesByUserId() {
        try {
            List<ResumeDTO> resumes = userService.getAllResumesByUserId();
            return ResponseEntity.ok(resumes);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

