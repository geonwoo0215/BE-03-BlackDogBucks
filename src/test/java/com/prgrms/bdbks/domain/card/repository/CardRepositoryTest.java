package com.prgrms.bdbks.domain.card.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.prgrms.bdbks.CustomDataJpaTest;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.repository.UserRepository;
import com.prgrms.bdbks.domain.user.role.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CustomDataJpaTest
class CardRepositoryTest {

	private User getUser() {
		return User.builder()
			.id(1L)
			.loginId("asdfasdf")
			.password("asdfasdf")
			.nickname("asdfasdf")
			.email("asdfas@naver.com")
			.birthDate(LocalDateTime.now().minusHours(5L))
			.phone("01012341234")
			.role(Role.USER)
			.build();
	}

	private Card getCard() {
		return Card.builder()
			.user(getUser())
			.build();
	}

	@BeforeEach()
	void setUp() {
		userRepository.save(getUser());
		cardRepository.save(getCard());
	}

	@Autowired
	CardRepository cardRepository;

	@Autowired
	UserRepository userRepository;

}