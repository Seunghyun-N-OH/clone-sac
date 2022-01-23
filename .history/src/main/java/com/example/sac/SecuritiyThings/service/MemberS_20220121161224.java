package com.example.sac.SecuritiyThings.service;

import com.example.sac.web.dtos.MembershipD;

public interface MemberS {

    // void certifiedPhoneNumber(String phone, String numStr);

    MembershipD joinMember(MembershipD md, String phone);

}
