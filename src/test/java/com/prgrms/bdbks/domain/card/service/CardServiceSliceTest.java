package com.prgrms.bdbks.domain.card.service;

import com.prgrms.bdbks.domain.card.dto.CardChargeRequest;
import com.prgrms.bdbks.domain.card.dto.CardChargeResponse;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.repository.CardRepository;
import com.prgrms.bdbks.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.createCard;
import static com.prgrms.bdbks.domain.testutil.CardObjectProvider.createCardRequest;
import static com.prgrms.bdbks.domain.testutil.UserObjectProvider.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceSliceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @DisplayName("charge - 사용자의 충전카드에 한도 내의 금액을 충전할 수 있다. - 성공")
    @Test
    void charge_validAmount_chargeSuccess() {
        //given
        Long userId = 1L;
        String cardId = UUID.randomUUID().toString();
        int amount = 50000;

        User user = createUser(userId);
        Card card = createCard(user, cardId);

        CardChargeRequest cardChargeRequest = createCardRequest(cardId, amount);
        Optional<Card> optionalCard = Optional.of(card);

        when(cardRepository.findById(cardId)).thenReturn(optionalCard);

        //when
        CardChargeResponse response = cardService.charge(userId, cardChargeRequest);

        //then
        verify(cardRepository).findById(cardId);

        assertThat(response)
                .hasFieldOrPropertyWithValue("cardId", cardChargeRequest.getCardId())
                .hasFieldOrPropertyWithValue("amount", cardChargeRequest.getAmount());
    }


}