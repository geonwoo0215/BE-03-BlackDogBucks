package com.prgrms.bdbks.domain.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardRefundResponse {

	private final String cardId;

	private final int amount;
}
