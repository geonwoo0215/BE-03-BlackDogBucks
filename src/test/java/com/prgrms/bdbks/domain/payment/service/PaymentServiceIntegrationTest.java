package com.prgrms.bdbks.domain.payment.service;

import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

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
class PaymentServiceIntegrationTest {

	private final PaymentService paymentService;

	private final CardRepository cardRepository;

	private final UserRepository userRepository;

	private final User user = UserObjectProvider.createUser();

	private User savedUser;

	private Card card;

	private Order order;

	@BeforeEach
	void setUp() {
		savedUser = userRepository.save(user);
		card = cardRepository.save(createCard(savedUser));
		order = new Order(1L);
	}

	//TODO Order entity 만들어지먼 orderPay 테스트 작성
	
	@ParameterizedTest
	@ValueSource(ints = {10000, 20000, 50000, 500000, 549999, 550000})
	@DisplayName("chargePay - 사용자의 충전카드에 금액을 충전할 수 있다. - 성공")
	void chargePay_validPrice_Success(int totalPrice) {
		assertDoesNotThrow(() -> paymentService.chargePay(card.getId(), totalPrice));
	}

	@ParameterizedTest
	@ValueSource(ints = {-50000, -500, 100, 200, 999, 550001, 10000000})
	@DisplayName("chargePay - 사용자의 충전카드에 한도를 벗어나는 금액은 충전할 수 없다. - 실패")
	void chargePay_InvalidPrice_Success(int totalPrice) {

		assertThrows(IllegalArgumentException.class, () -> paymentService.chargePay(card.getId(), totalPrice));
	}

}