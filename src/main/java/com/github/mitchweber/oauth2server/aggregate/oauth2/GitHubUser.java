package com.github.mitchweber.oauth2server.aggregate.oauth2;

import java.util.Collections;
import java.util.Map;

public class GitHubUser implements IOAuth2User {

    public static final String REGISTRATION_ID = "github";
    private final Map<String, Object> attributes;

    public GitHubUser(Map<String, Object> additionalParameters) {
        this.attributes = additionalParameters == null ? Collections.emptyMap() : additionalParameters;
    }

    public String getId() {
        return attributes.get("id") + "";
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getAuthProvider() {
        return GitHubUser.REGISTRATION_ID;
    }
}
