package com.github.mitchweber.oauth2server.controller;

import com.github.mitchweber.oauth2server.aggregate.User;
import com.github.mitchweber.oauth2server.controller.payload.SignUpRequest;
import com.github.mitchweber.oauth2server.service.UserService;
import com.github.mitchweber.oauth2server.controller.payload.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationManager authenticationManager;

    private UserDetailsManager userDetailsManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsManager = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Does login a user if the credentials are correct.
     *
     * @param loginRequest
     *
     * @return
     *      200 ok
     *      401 wrong credentials
     *      400 form errors e.g. fields not filled out (errors in response errors[index] {field, defaultMessage})
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("OK");
    }

    /**
     * Signs up a new user.
     *
     * @param signUpRequest
     *
     * @return
     *      200 ok
     *      400 form errors (errors in response errors[index] {field, defaultMessage})
     *
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        LOGGER.info("New SignUp");

        User dictionaryUser = new User(signUpRequest.getEmail(), null, "LOCAL");
        dictionaryUser.setFirstname(signUpRequest.getFirstName());
        dictionaryUser.setLastname(signUpRequest.getLastName());
        dictionaryUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        dictionaryUser.setTermsOfUse(signUpRequest.isTermsOfUse());

        this.userDetailsManager.createUser(dictionaryUser);

        return ResponseEntity.ok("OK");
    }
}

