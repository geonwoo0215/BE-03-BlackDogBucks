package com.prgrms.bdbks.domain.card.api;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.prgrms.bdbks.domain.card.dto.CardChargeRequest;
import com.prgrms.bdbks.domain.card.dto.CardChargeResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponses;
import com.prgrms.bdbks.domain.card.service.CardService;
import com.prgrms.bdbks.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

	private final CardService cardService;

	@GetMapping(value = "/charge", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CardSearchResponses> getCard(@SessionAttribute("user") User user) {
		CardSearchResponses cardSearchResponses = cardService.getCardList(user);
		return ResponseEntity.ok(cardSearchResponses);
	}

	@PatchMapping(value = "/charge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CardChargeResponse> charge(@SessionAttribute("user") User user,
		@RequestBody @Valid CardChargeRequest cardChargeRequest) {

		CardChargeResponse cardChargeResponse = cardService.charge(user.getId(), cardChargeRequest);
		return ResponseEntity.ok(cardChargeResponse);
	}
}
