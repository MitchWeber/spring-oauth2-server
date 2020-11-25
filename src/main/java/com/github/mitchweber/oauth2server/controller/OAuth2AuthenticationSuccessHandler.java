package com.github.mitchweber.oauth2server.controller;

import com.github.mitchweber.oauth2server.aggregate.PublicUser;
import com.github.mitchweber.oauth2server.aggregate.User;
import com.github.mitchweber.oauth2server.service.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private DefaultTokenServices tokenServices;

    private UserService userDetailsService;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository, DefaultTokenServices tokenServices, UserService userDetailsService) {
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.tokenServices = tokenServices;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect");
            return;
        }

        Map<String, Serializable> extensionProperties = Collections.emptyMap();
        Map<String, String> requestParameters = Collections.emptyMap();
        String clientId = "clientIdPassword";
        Collection<? extends GrantedAuthority> authorities = Collections.emptySet();
        Set<String> scopes = Collections.singleton("read");
        Set<String> resourceIds = Collections.emptySet();
        String oAuth2RedirectUri = null;
        Set<String> responseTypes = Collections.emptySet();

        OAuth2Request request1 = new OAuth2Request(requestParameters, clientId, authorities, true, scopes, resourceIds, oAuth2RedirectUri, responseTypes, extensionProperties);

        User user = this.userDetailsService.loadUser(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(), ((PublicUser) authentication.getPrincipal()).getName());
        PublicUser publicUser = new PublicUser(user.getEmail(), ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes(), user.getAuthorities());
        OAuth2AuthenticationToken oAuth2AuthenticationToken = new OAuth2AuthenticationToken(publicUser, publicUser.getAuthorities(), ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request1, oAuth2AuthenticationToken);

        OAuth2AccessToken accessToken = tokenServices.createAccessToken(oAuth2Authentication);

        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);


        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        LOGGER.debug("Target Url: " + targetUrl);

        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        HttpSession session = request.getSession();
        session.setAttribute("user", "user");
        session.setAttribute("username", "mitch");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(targetUrl);
    }
}
