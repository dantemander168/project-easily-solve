package com.learn.EasilySolveApp.services;

import com.learn.EasilySolveApp.constants.ExceptionCodes;
import com.learn.EasilySolveApp.exceptions.ValidationException;
import com.learn.EasilySolveApp.pojos.dto.auth.AuthResponse;
import com.learn.EasilySolveApp.pojos.dto.auth.LoginRequest;
import com.learn.EasilySolveApp.pojos.entities.UserEntity;
import com.learn.EasilySolveApp.repositories.UserRepository;
import com.learn.EasilySolveApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            validateLoginRequest(loginRequest);

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

    public ResponseEntity<?> register(UserEntity userEntity) {
        try {
            validateUserEntity(userEntity);

            userRepository.save(userEntity);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (ValidationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while processing the request"));
        }
    }

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            throw new ValidationException(ExceptionCodes.EMPTY_EMAIL);
        }
    }

    private void validateUserEntity(UserEntity userEntity) {
        if (userEntity.getEmail() == null || userEntity.getEmail().isEmpty()) {
            throw new ValidationException(ExceptionCodes.EMPTY_EMAIL);
        }
        if (!userEntity.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException(ExceptionCodes.INVALID_EMAIL);
        }
    }
}
