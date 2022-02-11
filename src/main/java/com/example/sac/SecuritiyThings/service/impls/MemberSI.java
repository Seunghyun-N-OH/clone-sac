package com.example.sac.SecuritiyThings.service.impls;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.example.sac.SecuritiyThings.dtos.OrdersD;
import com.example.sac.SecuritiyThings.dtos.SecureD;
import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.entities.OrdersE;
import com.example.sac.SecuritiyThings.repositories.MembershipR;
import com.example.sac.SecuritiyThings.repositories.OrdersR;
import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.domain.entities.Complaints;
import com.example.sac.domain.repositories.ComplaintsR;
import com.example.sac.web.dtos.MembershipD;

import org.json.simple.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

// 주석 업데이트 220126

@Service
public class MemberSI implements MemberS, UserDetailsService {

    public MemberSI(MembershipR m, PasswordEncoder pe, OrdersR or, ComplaintsR cr) {
        this.mr = m;
        this.pe = pe;
        this.or = or;
        this.cr = cr;
    }

    private final ComplaintsR cr;
    private final PasswordEncoder pe;
    private final MembershipR mr;
    private final OrdersR or;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Membership> tempEntity = mr.findById(username);
        if (tempEntity.isPresent()) {// input username is valid

            return new SecureD(tempEntity.get());
            // return SecureD type object to spring security check password

        } else {// username is invalid : mistyped or not a member
            String notexist = "User with id '" + username + "' not exist";

            throw new UsernameNotFoundException(notexist);
        }
        // method used for Login(spring security)
        // 1. get Entity(a single member) from Repository with given 'username'
        // 2. -(if exist) compare username&password. > 3.
        // -(if not) throws exception
        // 3. compare Username & Password
        // -(if matches) build SecureD object with 'roles', 'memberType'
        // then returns it as a 'UserDetails' type object
        // -(if not) throws exception
    }

    // 회원가입 중 인증번호 문자발송을 위한 api [coolSMS]
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {

        // 이거 api_ky, api_secret 어디 안전한데로 옮겨야할듯
        String api_key = "NCSY6EP4UOPTSDLW";
        String api_secret = "JQAERQHL9VYLHIAKBTA4USNF0PDWG29F";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber); // client's phone number
        params.put("from", "01055162461"); // sender
        params.put("type", "SMS");
        params.put("text", "(본인인증대체)휴대폰인증 테스트 : 인증번호 " + "[" + cerNum + "]");
        params.put("app_version", "test app 1.2");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

    // 회원가입 단계 중 정보입력단계로 넘어가면
    @Override
    public MembershipD joinMember(MembershipD md, String phone, RedirectAttributes ra) {
        // 010/1234/5678 세개로 나눠서 입력받아, 컨트롤러에서 합쳐둔걸 dto에 넣고
        md.setPhone(phone);
        // 그냥 들어있는 비밀번호를 encoding 된 값으로 바꿔주고
        md.setUserPw(pe.encode(md.getUserPw()));
        // membertype에 따라 그에 맞는 역할들 입력
        // TODO 그냥 String 되나 해봤는데 되네.. enum클래스 만들어서 활용할지 이후 결정
        Set<String> tmpRole = new HashSet<>();
        tmpRole.add("ROLE_USER");
        switch (md.getMemberType()) {
            case "junior":
                tmpRole.add("ROLE_FREE");
                tmpRole.add("ROLE_JUNIOR");
                break;
            case "adult":
                tmpRole.add("ROLE_FREE");
                tmpRole.add("ROLE_ADULT");
                break;
            case "senior":
                tmpRole.add("ROLE_FREE");
                tmpRole.add("ROLE_SENIOR");
                break;
            case "green":
                tmpRole.add("ROLE_PAID");
                tmpRole.add("ROLE_GREEN");
                break;
            case "blue":
                tmpRole.add("ROLE_PAID");
                tmpRole.add("ROLE_BLUE");
                break;
            case "gold":
                tmpRole.add("ROLE_PAID");
                tmpRole.add("ROLE_GOLD");
                break;
            case "admin":
                tmpRole.add("ROLE_PAID");
                tmpRole.add("ROLE_FREE");
                tmpRole.add("ROLE_JUNIOR");
                tmpRole.add("ROLE_ADULT");
                tmpRole.add("ROLE_SENIOR");
                tmpRole.add("ROLE_GREEN");
                tmpRole.add("ROLE_BLUE");
                tmpRole.add("ROLE_GOLD");
        }
        md.setRoles(tmpRole);
        ra.addFlashAttribute("error", "joined");
        ra.addFlashAttribute("exception", "회원가입 완료");
        // 이제 값이 다 저장된 entity를 save하고, 성공 시 리턴받는 entity를 dto로 바꿔서 가져감
        return new MembershipD(mr.save(md.toEntityForJoin()));
    }

    // 회원가입단계 아이디 중복체크
    @Override
    public boolean checkID(String tempID) {
        if (mr.findById(tempID).isPresent()) {// 지금 쓰고싶어하는 아이디가 이미 있으면
            return false; // 안돼
        } else { // 없으면
            return true; // 돼
        }
    }

    // 개인정보 수정페이지 진입 시 비밀번호 재확인
    @Override
    public String verifyUser(String pw, Principal p, Model m, RedirectAttributes ra) {
        // 들어온 비밀번호랑, principal의 userId로 DB에서 찾아온 계정의 userPw를 비교
        if (pe.matches(pw, new MembershipD(mr.findById(p.getName()).get()).getUserPw())) {
            // 일치하면 계정정보를 통으로 dto에 담아 모델에 넣어 리턴
            m.addAttribute("data", new MembershipD(mr.findById(p.getName()).get()));
            return "membership/info/myinfoEdit";
        }
        // 일치하지 않으면 다시 이전페이지인 myinfoEnter로 리디렉트하며 에러메시지 전달
        ra.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
        // TODO 자세히 알아봐바 | redirectattribute 이거 항상 작동하지는 않는거같은디.?
        return "redirect:/member/mypage/myinfoEnter";
    }

    // 바꾸고싶은거 바꾼다음 수정버튼 누름
    @Override
    public void updateMemberInfo(MembershipD a) {
        // 바뀐 정보를 update하기위해 담을 entity 선언
        Membership b;

        // 비밀번호 바꿨는지 안바꿨는지 확인후
        // 비밀번호 바꿨으면 dto에 담겨왔고, 아니면 비어있음
        if (a.getUserPw().isEmpty()) {
            // 안바꿨으면 지금 바꾸려는 유저id로 DB조회해서 나온 결과의 pw를 넣어서 entity build
            b = Membership.builder()
                    .memberType(a.getMemberType())
                    .roles(a.getRoles())
                    .userId(a.getUserId())
                    .userPw(mr.findById(a.getUserId()).get().getUserPw())
                    .name(a.getName())
                    .birthDate(a.getBirthDate())
                    .calendar(a.getCalendar())
                    .gender(a.getGender())
                    .addressPostal(a.getAddressPostal())
                    .address_1(a.getAddress_1())
                    .address_2(a.getAddress_2())
                    .phone(a.getPhone())
                    .email(a.getEmail())
                    .marketing_email(a.getMarketing_email())
                    .marketing_sms(a.getMarketing_sms())
                    .centerTerm(a.getCenterTerm())
                    .personalTerm(a.getPersonalTerm())
                    .marketingTerm(a.getMarketingTerm())
                    .build();
        } else {
            // 바꿨으면 기존비밀번호를 확인할 필요는 없으니 바로 entity build
            b = Membership.builder()
                    .memberType(a.getMemberType())
                    .roles(a.getRoles())
                    .userId(a.getUserId())
                    .userPw(pe.encode(a.getUserPw()))
                    .name(a.getName())
                    .birthDate(a.getBirthDate())
                    .calendar(a.getCalendar())
                    .gender(a.getGender())
                    .addressPostal(a.getAddressPostal())
                    .address_1(a.getAddress_1())
                    .address_2(a.getAddress_2())
                    .phone(a.getPhone())
                    .email(a.getEmail())
                    .marketing_email(a.getMarketing_email())
                    .marketing_sms(a.getMarketing_sms())
                    .centerTerm(a.getCenterTerm())
                    .personalTerm(a.getPersonalTerm())
                    .marketingTerm(a.getMarketingTerm())
                    .build();
        }
        // 비밀번호를 바꾸었는지 여부와 관계없이 이제 초기화된 entity를 save함수로 update
        mr.save(b);
    }

    // 회원이 탈퇴요청, 사유(옵션선택)와 코멘트(직접작성)을 함께 넘겨받음
    @Override
    public void kickoutMember(Principal p, String reason, String comment) {
        if (!reason.isBlank() && !comment.isBlank()) {
            cr.save(Complaints.builder().userId(p.getName()).reason(reason).comment(comment).build());
        }
        mr.deleteById(p.getName());
    }

    @Override
    public String getMembershipInfo(Model m, String userId) {
        List<OrdersE> orderHistory = or.findByBuyerUserId(userId);
        if (!orderHistory.isEmpty()) {
            List<OrdersD> orderList = new ArrayList<>();
            for (OrdersE a : orderHistory) {
                orderList.add(a.toDto());
            }
            m.addAttribute("orderList", orderList);
        }
        LocalDate memberSince = mr.findById(userId).get().getJoinedDate();
        m.addAttribute("joinedDate", memberSince);
        return "membership/info/myMembership";
    }

    @Override
    public String generateMUid(String memberClass, String product_id, String user) {
        return OrdersE.generateUid(product_id, user);
    }

    @Override
    @Transactional
    public String addOrderHistory(OrdersD data, int period) {
        data.setEffectiveDate(LocalDate.now());
        data.setExpiryDate(LocalDate.now().plusDays(period));
        or.save(data.toSaveEntity());

        Membership target = mr.findById(data.getBuyerUserId()).get();
        switch (data.getName()) {
            default:
                break;
            case "Blue-Membership":
                target.getRoles().add("ROLE_PAID");
                target.getRoles().add("ROLE_BLUE");
                break;
            case "Green-Membership":
                target.getRoles().add("ROLE_PAID");
                target.getRoles().add("ROLE_GREEN");
                break;
            case "Gold-Membership":
                target.getRoles().add("ROLE_PAID");
                target.getRoles().add("ROLE_GOLD");
                break;
        }

        return "/member/logout";
    }
}
