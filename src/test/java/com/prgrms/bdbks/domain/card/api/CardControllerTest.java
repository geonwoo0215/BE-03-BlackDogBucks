package com.prgrms.bdbks.domain.card.api;

import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.*;
import static com.prgrms.bdbks.domain.testutil.UserObjectProvider.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.bdbks.domain.card.dto.CardChargeRequest;
import com.prgrms.bdbks.domain.testutil.CardObjectProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.repository.CardRepository;
import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class CardControllerTest {

	private final HttpSession httpSession;
	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private static final String SESSION_USER = "user";

	private final ObjectMapper objectMapper;
	private final MockMvc mockMvc;
	private User user;
	private Card card;

	@BeforeEach
	void setUp() {
		user = createUser();
		userRepository.save(user);

		card = createCard(user);
		cardRepository.save(card);
		httpSession.setAttribute(SESSION_USER, user);
	}

	@Test
	@DisplayName("getCard - 로그인한 사용자의 충전카드 목록을 조회할 수 있다. - 성공")
	void getCard_validUser_ReturnCards() throws Exception {

		mockMvc.perform(get("/api/v1/cards/charge")
				.sessionAttr("user", user)
				.accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cardSearchResponses").exists())
			.andExpect(jsonPath("$.cardSearchResponses[0].cardId").value(card.getId()))
			.andExpect(jsonPath("$.cardSearchResponses[0].name").value(card.getName()))
			.andExpect(jsonPath("$.cardSearchResponses[0].amount").value(card.getAmount()))
			.andDo(print());
	}

	@Test
	@DisplayName("getCard - 사용자의 충전카드에 한도 내의 금액을 충전할 수 있다. - 성공")
	void charge_validAmount_chargeSuccess() throws Exception {

		String cardId = card.getId();
		int amount = 20000;

		CardChargeRequest cardChargeRequest = CardObjectProvider.createCardRequest(cardId, amount);

		String json = objectMapper.writeValueAsString(cardChargeRequest);

		mockMvc.perform(patch("/api/v1/cards/charge")
						.content(json)
						.contentType(APPLICATION_JSON)
						.sessionAttr("user", user)
						.accept(APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cardId").value(card.getId()))
				.andExpect(jsonPath("$.amount").value(card.getAmount()));
	}
}