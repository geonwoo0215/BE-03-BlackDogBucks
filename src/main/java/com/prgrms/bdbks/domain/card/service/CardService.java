package com.prgrms.bdbks.domain.card.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.common.exception.EntityNotFoundException;
import com.prgrms.bdbks.domain.card.dto.CardChargeRequest;
import com.prgrms.bdbks.domain.card.dto.CardChargeResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponse;
import com.prgrms.bdbks.domain.card.dto.CardSearchResponses;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.mapper.CardMapper;
import com.prgrms.bdbks.domain.card.repository.CardRepository;
import com.prgrms.bdbks.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

	private final CardMapper cardMapper;
	private final CardRepository cardRepository;

	public CardChargeResponse charge(Long userid, CardChargeRequest cardChargeRequest) {

		Card card = cardRepository.findById(cardChargeRequest.getCardId())
			.orElseThrow(() -> new EntityNotFoundException(Card.class, cardChargeRequest.getCardId()));
		card.compareUser(userid);
		card.chargeAmount(cardChargeRequest.getAmount());
		return CardChargeResponse.of(card.getId(), card.getAmount());
	}

	public CardSearchResponses getCardList(User user) {
		List<Card> cards = cardRepository.findByUserId(user.getId());

		List<CardSearchResponse> responses = cards.stream()
			.map(cardMapper::toCardSearchResponse)
			.collect(Collectors.toList());

		return CardSearchResponses.of(responses);
	}
}
