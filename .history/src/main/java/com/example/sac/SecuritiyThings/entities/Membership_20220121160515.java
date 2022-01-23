package com.example.sac.SecuritiyThings.entities;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "member_entity")
public class Membership {
    @Column(nullable = false)
    private String memberType;
    // member type
    // 'free_junior' 'free_adult' 'free_senior' 'green' 'blue' 'gold'
    // [to be added : corp]

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> roles = new HashSet<>();
    // authorized roles for each member
    // 'user' 'free' 'green' 'blue' 'gold' 'admin'
    // [to be added : corp]

    @Id
    private String userId;
    @Column(nullable = false)
    private String userPw;
    // TODO create & check rule

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    // YYYY-MM-DD
    @Column(nullable = false)
    private char calendar;

    @Column(nullable = false)
    private char gender;
    // 'm' or 'f'

    @Column(nullable = false)
    private String addressPostal;
    @Column(nullable = false)
    private String address_1;
    @Column(nullable = true)
    private String address_2;
    // divided into 3 parts to make editing easier

    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private char marketing_email;
    @Column(nullable = false)
    private char marketing_sms;
    @Column(nullable = false)
    private char centerTerm;
    @Column(nullable = false)
    private char personalTerm;
    @Column(nullable = false)
    private char marketingTerm;
    // whether user agrees or not to recieve marketing messages and terms
    // 'y' or 'n'
}
