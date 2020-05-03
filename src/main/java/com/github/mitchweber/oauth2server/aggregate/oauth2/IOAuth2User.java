package com.github.mitchweber.oauth2server.aggregate.oauth2;

public interface IOAuth2User {

    String getId();

    String getEmail();

    String getAuthProvider();
}
