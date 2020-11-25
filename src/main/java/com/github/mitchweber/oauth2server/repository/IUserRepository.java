package com.github.mitchweber.oauth2server.repository;

import com.github.mitchweber.oauth2server.aggregate.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> getByEmail(String email);

    Optional<User> findByProviderId(String id);

    void deleteByEmail(String email);

    Optional<User> findByProviderIdAndProvider(String providerId, String provider);
}
