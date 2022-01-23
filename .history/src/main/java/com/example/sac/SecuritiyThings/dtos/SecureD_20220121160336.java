package com.example.sac.SecuritiyThings.dtos;

// DTO that will be used with Spring Security while Login
@Getter
@Setter
@ToString // TODO to be deleted after implementation of Member Service
public class SecureD extends User {
    // ID / PW / ROLES : included by inheriting User class
    private String memberType;
    // required to check faster when redirecting pages

    public SecureD(Membership a) {
        super(a.getUserId(),
                a.getUserPw(),
                a.getRoles().stream().map(b -> new SimpleGrantedAuthority(b)).collect(Collectors.toSet()));
        this.memberType = a.getMemberType();
    }
}
