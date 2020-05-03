package com.github.mitchweber.oauth2server.repository;

import com.github.mitchweber.oauth2server.aggregate.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ListUserRepository implements IUserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public boolean existsByEmail(String email) {
        return getByEmail(email).isPresent();
    }

    @Override
    public void delete(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public void save(User userDetails) {
        users.add(userDetails);
    }

    @Override
    public void update(User userDetails) {
        // TODO: Implement...

    }

    @Override
    public Optional<User> findByProviderId(String id) {
        return users.stream().filter(user -> user.getProviderId().equals(id)).findFirst();
    }
}
