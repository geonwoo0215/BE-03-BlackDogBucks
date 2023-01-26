package com.prgrms.bdbks.domain.payment.facade;

import static com.prgrms.bdbks.domain.coupon.service.CouponService.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.service.CardService;
import com.prgrms.bdbks.domain.coupon.service.CouponService;
import com.prgrms.bdbks.domain.order.entity.Order;
import com.prgrms.bdbks.domain.payment.dto.PaymentChargeRequest;
import com.prgrms.bdbks.domain.payment.dto.PaymentOrderRequest;
import com.prgrms.bdbks.domain.payment.service.PaymentService;
import com.prgrms.bdbks.domain.star.entity.Star;
import com.prgrms.bdbks.domain.star.service.StarService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentFacadeService {
	//TODO 파사드 테스트, 결제 취소
	private final PaymentService paymentService;
	private final StarService starService;
	private final CardService cardService;
	private final CouponService couponService;

	@Transactional
	public void orderPay(Order order, PaymentOrderRequest paymentOrderRequest) {
		Card card = cardService.findByCardId(paymentOrderRequest.getCardId());

		cardService.pay(card, paymentOrderRequest.getTotalPrice());

		paymentService.orderPay(order, card, paymentOrderRequest.getTotalPrice());

		starService.updateCount(paymentOrderRequest.getUserId(), paymentOrderRequest.getItemCount(), paymentOrderRequest.getCouponUsed());
	}

	@Transactional
	public void chargePay(Long userId, PaymentChargeRequest paymentChargeRequest) {
		cardService.charge(userId,
			paymentChargeRequest.getCardId(),
			paymentChargeRequest.getAmount());

		paymentService.chargePay(paymentChargeRequest.getCardId(), paymentChargeRequest.getAmount());

		if (paymentChargeRequest.getAmount() >= PRICE_CONDITION) { // 얘도 책임을 쿠폰 서비스로 넘길 수  듯?을있있
			couponService.create(userId);
		}
	}
}
