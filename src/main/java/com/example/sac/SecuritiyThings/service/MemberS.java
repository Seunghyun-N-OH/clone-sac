package com.example.sac.SecuritiyThings.service;

import java.security.Principal;

import com.example.sac.web.dtos.MembershipD;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MemberS {

    void certifiedPhoneNumber(String phone, String numStr);

    MembershipD joinMember(MembershipD md, String phone);

    boolean checkID(String tempID);

    String verifyUser(String pw, Principal p, Model m, RedirectAttributes ra);

    void updateMemberInfo(MembershipD a);

    void kickoutMember(Principal p, String reason, String comment);

}
