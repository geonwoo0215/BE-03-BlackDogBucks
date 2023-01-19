package com.prgrms.bdbks.domain.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.prgrms.bdbks.domain.card.dto.CardSearchResponse;
import com.prgrms.bdbks.domain.card.entity.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {
	CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	@Mappings({
		@Mapping(source = "id", target = "cardId"),
		@Mapping(source = "name", target = "name"),
		@Mapping(source = "amount", target = "amount")
	})
	CardSearchResponse toCardSearchResponse(Card card);
}

/**
 * Card
 * - String id
 * - int amount
 * - User user
 *
 *
 * CardChargeRequest
 * - int amount
 *
 * @Mapping(target = "
 *
 *
 */