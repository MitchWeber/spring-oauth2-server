package com.github.mitchweber.oauth2server.aggregate;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private boolean termsOfUse;

    private boolean emailVerified;

    private String password;

    private String provider;

    private String providerId;

    private Map<String, Object> attributes;

    public User(String email, String providerId, String provider) {
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean isTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(boolean termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(DEFAULT_ROLE_PREFIX + "USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "DomainDictionaryUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", termsOfUse=" + termsOfUse +
                ", emailVerified=" + emailVerified +
                ", password='" + password + '\'' +
                ", provider='" + provider + '\'' +
                ", providerId='" + providerId + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
