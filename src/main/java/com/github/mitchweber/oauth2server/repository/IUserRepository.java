package com.github.mitchweber.oauth2server.repository;

import com.github.mitchweber.oauth2server.aggregate.User;

import java.util.Optional;

public interface IUserRepository {

    boolean existsByEmail(String email);

    void delete(String email);

    Optional<User> getByEmail(String email);

    void save(User userDetails);

    void update(User userDetails);

    Optional<User> findByProviderId(String id);
}
