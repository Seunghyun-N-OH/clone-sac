package com.example.sac.SecuritiyThings.service.impls;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.sac.SecuritiyThings.dtos.SecureD;
import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.repositories.MembershipR;
import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.web.dtos.MembershipD;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        System.out.println(username); // TODO delete this line after implementation
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

    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {

        String api_key = "NCSY6EP4UOPTSDLW";
        String api_secret = "JQAERQHL9VYLHIAKBTA4USNF0PDWG29F";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber); // 수신전화번호
        params.put("from", "01055162461"); // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
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

    @Override
    public MembershipD joinMember(MembershipD md, String phone) {
        md.setPhone(phone);
        md.setUserPw(pe.encode(md.getUserPw()));
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
        }
        md.setRoles(tmpRole);
        return new MembershipD(mr.save(md.toEntityForJoin()));
    }
}
