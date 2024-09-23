package com.davidiwezulu.project.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    // Getters and Setters
}

