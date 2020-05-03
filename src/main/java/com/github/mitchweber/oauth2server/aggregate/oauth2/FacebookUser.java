package com.github.mitchweber.oauth2server.aggregate.oauth2;

import java.util.Collections;
import java.util.Map;

public class FacebookUser implements IOAuth2User {

    public static final String REGISTRATION_ID = "facebook";
    private Map<String, Object> attributes;

    public FacebookUser(Map<String, Object> additionalParameters) {
        this.attributes = additionalParameters == null ? Collections.emptyMap() : additionalParameters;
    }

    public String getId() {
        return (String) attributes.get("id");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getAuthProvider() {
        return FacebookUser.REGISTRATION_ID;
    }

}
