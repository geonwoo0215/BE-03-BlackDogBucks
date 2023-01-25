package com.prgrms.bdbks.domain.payment.facade;

import static com.prgrms.bdbks.domain.coupon.service.CouponService.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.service.CardService;
import com.prgrms.bdbks.domain.coupon.service.CouponService;
import com.prgrms.bdbks.domain.payment.dto.PaymentChargeRequest;
import com.prgrms.bdbks.domain.payment.dto.PaymentOrderRequest;
import com.prgrms.bdbks.domain.payment.entity.Order;
import com.prgrms.bdbks.domain.payment.service.PaymentService;
import com.prgrms.bdbks.domain.star.entity.Star;
import com.prgrms.bdbks.domain.star.service.StarService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentFacadeService {
	private final PaymentService paymentService;
	private final StarService starService;
	private final CardService cardService;
	private final CouponService couponService;

	public void orderPay(Order order, PaymentOrderRequest paymentOrderRequest) {
		Card card = cardService.getCard(paymentOrderRequest.getCardId());

		cardService.pay(card, paymentOrderRequest.getTotalPrice());

		paymentService.orderPay(order, card, paymentOrderRequest.getTotalPrice());

		Star star = starService.findById(paymentOrderRequest.getUserId());

		if (!paymentOrderRequest.getCouponUsed()) {
			starService.updateCount(star, paymentOrderRequest.getCount());
		}
	}

	public void chargePay(Long userId, PaymentChargeRequest paymentChargeRequest) {
		//TODO 5만원 이상 충전 시 쿠폰 생성
		cardService.charge(userId,
			paymentChargeRequest.getCardId(),
			paymentChargeRequest.getAmount());

		paymentService.chargePay(paymentChargeRequest.getCardId(), paymentChargeRequest.getAmount());

		if (paymentChargeRequest.getAmount() >= PRICE_CONDITION) {
			couponService.create(userId);
		}

	}
}
