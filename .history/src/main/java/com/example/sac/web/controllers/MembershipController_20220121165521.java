package com.example.sac.web.controllers;

import java.util.Random;

import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.web.dtos.MembershipD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MembershipController {

    public MembershipController(MemberS a) {
        this.ms = a;
    }

    private final MemberS ms;

    @RequestMapping(value = "/member/signup", method = RequestMethod.GET)
    public String getJoinPage() {
        return "membership/join";
    }

    @RequestMapping(value = "/member/signup/step1", method = RequestMethod.GET)
    public String getAgrees(Model m, @RequestParam(defaultValue = "adult") String t) {
        m.addAttribute("memberType", t);
        return "membership/join1";
    }

    @RequestMapping(value = "/member/signup/step2", method = RequestMethod.POST)
    public String getIdentityCheck(Model m, String memberType, char centerAgreement, char personalAgreement,
            char marketingAgreement) {
        m.addAttribute("memberType", memberType);
        m.addAttribute("center", centerAgreement);
        m.addAttribute("personal", personalAgreement);
        m.addAttribute("marketing", marketingAgreement);
        return "membership/join-2";
    }

    @ResponseBody
    @RequestMapping(value = "/member/signup/verification", method = RequestMethod.GET)
    public String getVerified(String phoneNumber) {
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        ms.certifiedPhoneNumber(phoneNumber, numStr);
        return numStr;
    }

    @RequestMapping(value = "/member/signup/step3", method = RequestMethod.POST)
    public String getInfoPage(Model m, String memberType, char centerAgreement, char personalAgreement,
            char marketingAgreement,
            String name, String birthday) {
        m.addAttribute("memberType", memberType);
        m.addAttribute("center", centerAgreement);
        m.addAttribute("personal", personalAgreement);
        m.addAttribute("marketing", marketingAgreement);
        m.addAttribute("name", name);
        m.addAttribute("birthday", birthday);
        return "membership/join-3";
    }

    @RequestMapping(value = "/member/signup/step4", method = RequestMethod.POST)
    public String joined(Model m, MembershipD md, String phone1, String phone2, String phone3) {
        m.addAttribute("joined", ms.joinMember(md, phone1 + phone2 + phone3));
        return "membership/join-4";
    }

    @RequestMapping(value = "/member/signin", method = RequestMethod.GET)
    public String getLoginForm() {
        return "membership/login";
    }

    @RequestMapping(value = "/member/signin", method = RequestMethod.GET)
    public String loginFaild(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/login/login";
    }

    @RequestMapping(value = "/member/find-id", method = RequestMethod.GET)
    public String toFindId() {
        return "membership/findId";
    }

    @RequestMapping(value = "/member/find-pw", method = RequestMethod.GET)
    public String toFindPw() {
        return "membership/findPw";
    }

    @RequestMapping(value = "/member/premium/benefits", method = RequestMethod.GET)
    public String toPremiumBenefits() {
        return "membership/premium";
    }

    @RequestMapping(value = "/member/premium/affiliates", method = RequestMethod.GET)
    public String toPremiumAffiliates() {
        return "membership/premium-affiliates";
    }
}
