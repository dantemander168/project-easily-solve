package com.learn.EasilySolveApp.controllers;

import com.learn.EasilySolveApp.pojos.dto.auth.AuthResponse;
import com.learn.EasilySolveApp.pojos.dto.auth.LoginRequest;
import com.learn.EasilySolveApp.pojos.entities.UserEntity;
import com.learn.EasilySolveApp.repositories.UserRepository;
import com.learn.EasilySolveApp.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()));

            if (userEntity.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not found"));
            }

            String token = jwtUtil.generateToken(loginRequest.getEmail());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while processing the request"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity userEntity){
        try{
            userRepository.save(userEntity);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while processing the request"));
        }
    }
}
