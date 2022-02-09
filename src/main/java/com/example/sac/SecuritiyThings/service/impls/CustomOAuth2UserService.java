package com.example.sac.SecuritiyThings.service.impls;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.repositories.MembershipR;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService /* implements OAuth2UserService<OAuth2UserRequest, OAuth2User> */ {

    // public CustomOAuth2UserService(MembershipR mr, HttpSession hs) {
    // this.mr = mr;
    // this.hs = hs;
    // }

    // private final MembershipR mr;
    // private final HttpSession hs;

    // @Override
    // public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
    // OAuth2AuthenticationException {
    // OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new
    // DefaultOAuth2UserService();
    // OAuth2User oAuth2User = delegate.loadUser(userRequest);

    // String registrationId =
    // userRequest.getClientRegistration().getRegistrationId();
    // String userNameAttributeName =
    // userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
    // .getUserNameAttributeName();

    // OAuthAttributes attributes = OAuthAttributes.of(registrationId,
    // userNameAttributeName,
    // oAuth2User.getAttributes());
    // Membership m = saveOrUpdate(attributes);
    // hs.setAttribute("user", new SessionUser(user));

    // return new DefaultOAuth2User(Collections.singleton(new
    // SimpleGrantedAuthority(m.getRoles())),
    // attributes.getAttributes(), attributes.getNameAttributeKey());
    // }

}
