package com.example.sac.web.dtos;

import java.time.LocalDate;

import com.example.sac.SecuritiyThings.entities.Membership;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MembershipD {
    private String memberType;
    private Set<String> roles;
    private String userId;
    private String userPw;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private char calendar;

    private char gender;

    private String addressPostal;
    private String address_1;
    private String address_2;

    private String phone;
    private String email;

    private char marketing_email;
    private char marketing_sms;
    private char centerTerm;
    private char personalTerm;
    private char marketingTerm;

    public MembershipD(Membership a) {
        this.memberType = a.getMemberType();
        this.roles = a.getRoles();
        this.userId = a.getUserId();
        this.userPw = a.getUserPw();
        this.name = a.getName();
        this.birthDate = a.getBirthDate();
        this.calendar = a.getCalendar();
        this.gender = a.getGender();
        this.addressPostal = a.getAddressPostal();
        this.address_1 = a.getAddress_1();
        this.address_2 = a.getAddress_2();
        this.phone = a.getPhone();
        this.email = a.getEmail();
        this.centerTerm = a.getCenterTerm();
        this.personalTerm = a.getPersonalTerm();
        this.marketingTerm = a.getMarketingTerm();
        this.marketing_email = a.getMarketing_email();
        this.marketing_sms = a.getMarketing_sms();
    }

    public Membership toEntityForJoin() {
        return Membership.builder()
                .memberType(this.getMemberType())
                .roles(this.getRoles())
                .userId(this.getUserId())
                .userPw(this.getUserPw())
                .name(this.getName())
                .birthDate(this.getBirthDate())
                .calendar(this.getCalendar())
                .gender(this.getGender())
                .addressPostal(this.getAddressPostal())
                .address_1(this.getAddress_1())
                .address_2(this.getAddress_2())
                .phone(this.getPhone())
                .email(this.getEmail())
                .centerTerm(this.getCenterTerm())
                .personalTerm(this.getPersonalTerm())
                .marketingTerm(this.getMarketingTerm())
                .marketing_email(this.getMarketing_email())
                .marketing_sms(this.getMarketing_sms())
                .build();
    }
}
