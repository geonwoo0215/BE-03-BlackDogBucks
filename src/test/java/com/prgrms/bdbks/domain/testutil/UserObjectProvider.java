package com.prgrms.bdbks.domain.testutil;

import java.time.LocalDateTime;

import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.role.Role;

public class UserObjectProvider {

	public static User createUser() {
		return User.builder()
			.birthDate(LocalDateTime.now().minusYears(26L))
			.email("test@naver.com")
			.loginId("test1234")
			.password("password1234")
			.nickname("이디야화이팅")
			.phone("01012341234")
			.role(Role.USER)
			.build();
	}

}
