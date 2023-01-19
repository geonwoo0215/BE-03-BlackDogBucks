package com.prgrms.bdbks.domain.card.service;

import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.dto.CardSearchResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponses;
import com.prgrms.bdbks.domain.card.repository.CardRepository;
import com.prgrms.bdbks.domain.testutil.UserObjectProvider;
import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CardServiceTest {

	private final UserRepository userRepository;

	private final CardRepository cardRepository;

	private final CardService cardService;

	private final User user = UserObjectProvider.createUser();

	@BeforeEach
	void setUp() {
		User savedUser = userRepository.save(user);
		cardRepository.save(createCard(savedUser));
	}

	/**
	 * @DisplayName("메소드 명- 설명 - 생성 or 실패")
	 * 	void validatePassword_EmptyValue_ExceptionThrown(String invalidPassword) {
	 */

	@Test
	@DisplayName("getCardList - 로그인한 사용자의 충전카드 목록을 조회할 수 있다. - 성공")
	void getCardList_validUser_ReturnCards() {

		//when
		CardSearchResponses responses = cardService.getCardList(user);

		List<CardSearchResponse> cardResponseList = responses.getCardSearchResponses();

		//then
		assertThat(cardResponseList.size()).isEqualTo(1);
	}

}