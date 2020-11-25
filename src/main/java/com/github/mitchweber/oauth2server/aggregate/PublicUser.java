package com.github.mitchweber.oauth2server.aggregate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PublicUser implements OAuth2User {

    private String name;
    private Map<String, Object> attributes;
    private List<? extends GrantedAuthority> authorities;

    public PublicUser(String name, Map<String, Object> attributes, List<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(this.authorities);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
