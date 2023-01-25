package com.prgrms.bdbks.domain.card.service;

import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.dto.CardChargeRequest;
import com.prgrms.bdbks.domain.card.dto.CardChargeResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponses;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.repository.CardRepository;
import com.prgrms.bdbks.domain.testutil.UserObjectProvider;
import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CardServiceIntegrationTest {

	private final UserRepository userRepository;

	private final CardRepository cardRepository;

	private final CardService cardService;

	private final User user = UserObjectProvider.createUser();
	private User savedUser;

	private Card card;

	@BeforeEach
	void setUp() {
		savedUser = userRepository.save(user);
		card = cardRepository.save(createCard(savedUser));
	}

	/**
	 * @DisplayName("메소드 명- 설명 - 생성 or 실패")
	 * void validatePassword_EmptyValue_ExceptionThrown(String invalidPassword) {
	 */

	@DisplayName("getCardList - 로그인한 사용자의 충전카드 목록을 조회할 수 있다. - 성공")
	@Test
	void getCardList_validUser_ReturnCards() {

		//when
		CardSearchResponses responses = cardService.getCardList(user);

		List<CardSearchResponse> cardResponseList = responses.getCardSearchResponses();

		//then
		assertThat(cardResponseList.size()).isEqualTo(1);
	}

	@DisplayName("charge - 사용자의 충전카드에 한도 내의 금액을 충전할 수 있다. - 성공")
	@ParameterizedTest
	@ValueSource(ints = {10000, 20000, 50000, 300000, 550000})
	void charge_validAmount_chargeSuccess(int amount) {
		//given
		CardChargeRequest cardChargeRequest = new CardChargeRequest(card.getId(), amount);

		//when
		CardChargeResponse chargeResponse = cardService.charge(savedUser.getId(), card.getId(), amount);

		//then
		assertThat(chargeResponse)
			.hasFieldOrPropertyWithValue("cardId", card.getId())
			.hasFieldOrPropertyWithValue("amount", card.getAmount());
	}

	@DisplayName("charge - 사용자의 충전카드에 한도를 벗어나는 금액은 충전할 수 없다. - 실패")
	@ParameterizedTest
	@ValueSource(ints = {-50000, -2500, 1000, 9999, 550001, 10000000})
	void charge_invalidAmount_exceptionThrown(int amount) {
		//given
		CardChargeRequest cardChargeRequest = new CardChargeRequest(card.getId(), amount);

		//when
		assertThrows(IllegalArgumentException.class, () -> cardService.charge(savedUser.getId(), card.getId(), amount));
	}
}