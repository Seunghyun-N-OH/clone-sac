package com.example.sac.SecuritiyThings.service;

public interface MemberS {

    void certifiedPhoneNumber(String phone, String numStr);

    MembershipD joinMember(MembershipD md, String phone);

}
