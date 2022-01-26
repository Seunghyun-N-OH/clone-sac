package com.example.sac;

import java.time.LocalDate;

import com.example.sac.SecuritiyThings.service.MemberS;
import com.example.sac.web.dtos.MembershipD;

// import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableJpaAuditing
class SacApplicationTests {

	@Autowired
	MemberS ms;

	// @Test
	void createAdminMember() {
		MembershipD data = MembershipD.builder()
				.memberType("admin")
				.userId("admin01")
				.userPw("0000")
				.name("Administrator")
				.birthDate(LocalDate.of(1, 1, 1))
				.calendar('l')
				.gender('m')
				.addressPostal("11111")
				.address_1("admin address 1")
				.address_2("admin address 2")
				.phone("01011112222")
				.email("admin@admin.admin")
				.marketingTerm('y')
				.centerTerm('y')
				.personalTerm('y')
				.marketing_email('y')
				.marketing_sms('y')
				.build();
		System.out.println(ms.joinMember(data, "55544446666"));
	}

}
