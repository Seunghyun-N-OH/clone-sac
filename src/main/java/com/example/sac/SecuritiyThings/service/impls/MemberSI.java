package com.example.sac.SecuritiyThings.service.impls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.sac.SecuritiyThings.dtos.SecureD;
import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.domain.repositories.MembershipR;
import com.example.sac.web.dtos.MembershipD;

import org.json.simple.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

// 주석 업데이트 220126

@Service
public class MemberSI implements MemberS, UserDetailsService {

    public MemberSI(MembershipR m, PasswordEncoder pe) {
        this.mr = m;
        this.pe = pe;
    }

    private final PasswordEncoder pe;
    private final MembershipR mr;

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

        String api_key = "NCSY6EP4UOPTSDLW";
        String api_secret = "JQAERQHL9VYLHIAKBTA4USNF0PDWG29F";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber); // phone number of the user(visitor) that needs to receive a code
        params.put("from", "01055162461"); // sender : registration required before using
        params.put("type", "SMS");
        params.put("text", "(본인인증대체)휴대폰인증 테스트 : 인증번호 " + "[" + cerNum + "]");
        params.put("app_version", "test app 1.2"); // application name and version

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
    public MembershipD joinMember(MembershipD md, String phone) {
        // 010/1234/5678 세개로 나눠서 입력받아, 컨트롤러에서 합쳐둔걸 dto에 넣고
        md.setPhone(phone);
        // 그냥 들어있는 비밀번호를 encoding 된 값으로 바꿔주고
        md.setUserPw(pe.encode(md.getUserPw()));
        // membertype에 따라 그에 맞는 역할들 입력
        // TODO enum클래스 활용할지 이후 결정
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
}
