package com.prgrms.bdbks.common.exception;

import org.junit.jupiter.api.Test;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.user.entity.User;

class EntityNotFoundExceptionTest {

	@Test
	void test1() {
		EntityNotFoundException entityNotFoundException = new EntityNotFoundException(User.class, 1L);

		EntityNotFoundException entityNotFoundException1 = new EntityNotFoundException(Card.class, "id1", "id2", "id3",
			"yourName", 1, 99L);
		System.out.println(entityNotFoundException.getMessage());

		System.out.println(entityNotFoundException1.getMessage());

	}
}