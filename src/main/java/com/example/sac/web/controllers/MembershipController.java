package com.example.sac.web.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Random;

import com.example.sac.SecuritiyThings.dtos.OrdersD;
import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.web.dtos.MembershipD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String joined(Model m, MembershipD md, String phone1, String phone2, String phone3, RedirectAttributes ra) throws UnsupportedEncodingException {
        m.addAttribute("joined", ms.joinMember(md, phone1 + phone2 + phone3, ra));
        // return "membership/join-4";
        return "redirect:/member/signin?error=true&exception=" + URLEncoder.encode("회원가입 성공", "UTF-8");
    }

    @RequestMapping(value = "/member/signin", method = RequestMethod.GET)
    public String loginFaild(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "membership/login";
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

    @ResponseBody
    @RequestMapping(value = "/member/signup/checkidavailability", method = RequestMethod.GET)
    public boolean isThisIdAvailable(String tempID) {
        return ms.checkID(tempID);
    }

    @RequestMapping(value = "/member/mypage/myTicket", method = RequestMethod.GET)
    public String toMyticket() {
        return "membership/info/myTicket";
    }

    @RequestMapping(value = "/member/mypage/myLecture", method = RequestMethod.GET)
    public String toMylecture() {
        return "membership/info/myLecture";
    }

    @RequestMapping(value = "/member/mypage/myPick", method = RequestMethod.GET)
    public String toMyPick() {
        return "membership/info/myPick";
    }

    @RequestMapping(value = "/member/mypage/myActivity", method = RequestMethod.GET)
    public String toMyActivity() {
        return "membership/info/myActivity";
    }

    @RequestMapping(value = "/member/mypage/myinfoEnter", method = RequestMethod.GET)
    public String toMyinfoEnter() {
        return "membership/info/myinfoEnter";
    }

    @RequestMapping(value = "/member/mypage/myinfoEdit", method = RequestMethod.POST)
    public String toMyinfoEdit(String pw, Principal p, Model m, RedirectAttributes ra) {
        return ms.verifyUser(pw, p, m, ra);
    }

    @RequestMapping(value = "/member/mypage/myinfoEdit", method = RequestMethod.PUT)
    public String submitEdit(MembershipD a) {
        ms.updateMemberInfo(a);
        return "redirect:/member/logout";
    }

    @RequestMapping(value = "/member/mypage/myMembership", method = RequestMethod.GET)
    public String toMyMembership(Model m, Principal p) {
        return ms.getMembershipInfo(m, p.getName());
    }

    @RequestMapping(value = "/member/mypage/myDonation", method = RequestMethod.GET)
    public String toMyDonation() {
        return "membership/info/myDonation";
    }

    @RequestMapping(value = "/member/mypage/myMembershipResign", method = RequestMethod.GET)
    public String toMyMembershipResign() {
        return "membership/info/myMembershipResign";
    }

    @RequestMapping(value = "/member/mypage/myMembershipResign", method = RequestMethod.DELETE)
    public String resign(Principal p, String reason, String comment) {
        ms.kickoutMember(p, reason, comment);
        return "redirect:/member/logout";
    }

    @ResponseBody
    @RequestMapping(value = "/member/premium/initPurchase", method = RequestMethod.POST)
    public String generateMUid(String memberClass, String product_id, String user) {
        return ms.generateMUid(memberClass, product_id, user);
    }

    @ResponseBody
    @RequestMapping(value = "/member/premium/success", method = RequestMethod.POST)
    public String generateMUid(OrdersD data, int period) {
        return ms.addOrderHistory(data, period);
    }

    @RequestMapping(value = "/member/signin/naver", method = RequestMethod.GET)
    public String naverCallback() {
        return "membership/sns/naver-callback";
    }

    @RequestMapping(value = "/member/mypage/resignation", method = RequestMethod.GET)
    public String resignationConfirmWindow(Principal p) {
        return "membership/info/resignation-confirm";
    }
}
