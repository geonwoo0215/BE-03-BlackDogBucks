package com.prgrms.bdbks.domain.card.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardChargeRequest {

	@NotBlank(message = "충전할 카드의 ID를 입력해주세요.")
	private final String cardId;

	@Min(value = 10000, message = "최소 충전 금액은 10000원 입니다.")
	@Max(value = 550000, message = "최대 충전 금액은 550000원 입니다.")
	private final int amount;

}
