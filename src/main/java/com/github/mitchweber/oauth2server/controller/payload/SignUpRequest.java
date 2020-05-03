package com.github.mitchweber.oauth2server.controller.payload;

import com.github.mitchweber.oauth2server.controller.validator.UniqueEmail;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class SignUpRequest {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    /**
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     */
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(){}|/?><,.:;'\"_-~])(?=\\S+$).{8,}$", message = "Password must have at least 1 digit, a lower case letter, an upper case letter, a special character, no whitespace and must be alt least 8 characters long")
    private String password;

    @AssertTrue(message = "{validation.termsOfUse}")
    private boolean termsOfUse;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isTermsOfUse() {
        return termsOfUse;
    }
}
