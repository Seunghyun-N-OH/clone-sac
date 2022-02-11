package com.example.sac.SecuritiyThings.dtos;

import java.util.stream.Collectors;

import com.example.sac.SecuritiyThings.entities.Membership;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

// DTO that will be used with Spring Security while Login
@Getter
@Setter
public class SecureD extends User {
    // ID / PW / ROLES : inherited from User class
    private String memberType;
    private String name;

    public SecureD(Membership a) {
        super(a.getUserId(),
                a.getUserPw(),
                a.getRoles().stream().map(b -> new SimpleGrantedAuthority(b)).collect(Collectors.toSet()));
        this.memberType = a.getMemberType();
        this.name = a.getName();
    }
}