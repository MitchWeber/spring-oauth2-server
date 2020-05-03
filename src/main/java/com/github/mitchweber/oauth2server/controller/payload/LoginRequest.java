package com.github.mitchweber.oauth2server.controller.payload;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
