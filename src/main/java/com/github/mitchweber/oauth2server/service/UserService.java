package com.github.mitchweber.oauth2server.service;

import com.github.mitchweber.oauth2server.aggregate.PublicUser;
import com.github.mitchweber.oauth2server.aggregate.User;
import com.github.mitchweber.oauth2server.aggregate.oauth2.FacebookUser;
import com.github.mitchweber.oauth2server.aggregate.oauth2.GitHubUser;
import com.github.mitchweber.oauth2server.aggregate.oauth2.IOAuth2User;
import com.github.mitchweber.oauth2server.exception.UserAlreadyExistsException;
import com.github.mitchweber.oauth2server.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

@Service
public class UserService extends DefaultOAuth2UserService implements UserDetailsManager, UserDetailsPasswordService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        IOAuth2User user = getDomainDictionaryOAuth2User(oAuth2UserRequest, oAuth2User);

        Optional<User> domainDictionaryUser = userRepository.findByProviderId(user.getId());

        User internalUser;
        if (domainDictionaryUser.isPresent()) {
            // A normal Login
            this.updateUser(domainDictionaryUser.get());
            internalUser = domainDictionaryUser.get();
        } else {
            // A new Signup
            User userDetails = new User(user.getEmail(), user.getId(), user.getAuthProvider());
            this.createUser(userDetails);
            internalUser = userDetails;
        }

        PublicUser publicUser = new PublicUser(
                oAuth2User.getName(),
                oAuth2User.getAttributes(),
                internalUser.getAuthorities());

        return publicUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String s) {
        // TODO: Implement
        throw new NotImplementedException();
    }

    @Override
    public void createUser(UserDetails userDetails) {

        if (userRepository.existsByEmail(userDetails.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        System.out.println(userDetails.toString());
        userRepository.save((User) userDetails);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        System.out.println(userDetails.toString());
        userRepository.update((User) userDetails);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.delete(email);
    }

    @Override
    public void changePassword(String s, String s1) {
        // TODO: Implement
        throw new NotImplementedException();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private IOAuth2User getDomainDictionaryOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        switch (oAuth2UserRequest.getClientRegistration().getRegistrationId()) {
            case GitHubUser.REGISTRATION_ID:
                return new GitHubUser(oAuth2User.getAttributes());
            case FacebookUser.REGISTRATION_ID:
                return new FacebookUser(oAuth2User.getAttributes());
            default:
                throw new IllegalArgumentException("Unknown registration: " + oAuth2UserRequest.getClientRegistration().getRegistrationId());
        }
    }
}
