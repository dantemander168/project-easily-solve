package com.learn.EasilySolveApp.constants;

import lombok.Getter;

@Getter
public enum ExceptionCodes {
    EMPTY_EMAIL("Email is required"),
    INVALID_EMAIL("Invalid email format");

    private final String message;

    ExceptionCodes(String message) {
        this.message = message;
    }
}
