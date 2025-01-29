package com.learn.EasilySolveApp.pojos.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String email;

    public LoginRequest() {
    }

    public LoginRequest(String email) {
        this.email = email;
    }
}
