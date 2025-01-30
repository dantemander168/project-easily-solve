package com.learn.EasilySolveApp.pojos.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
}
