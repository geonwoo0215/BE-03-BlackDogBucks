package com.prgrms.bdbks.domain.testutil;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.user.entity.User;

public class CardObjectProvider {

	public static Card createCard(User user) {
		return Card.builder()
			.user(user)
			.name("카드카드카드")
			.build();
	}

}