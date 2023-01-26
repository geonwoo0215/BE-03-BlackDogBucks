package com.prgrms.bdbks.domain.payment.facade;

import static com.prgrms.bdbks.domain.coupon.service.CouponService.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.card.service.CardService;
import com.prgrms.bdbks.domain.coupon.service.CouponService;
import com.prgrms.bdbks.domain.order.entity.Order;
import com.prgrms.bdbks.domain.payment.dto.PaymentChargeRequest;
import com.prgrms.bdbks.domain.payment.dto.PaymentOrderCancelRequest;
import com.prgrms.bdbks.domain.payment.dto.PaymentOrderRequest;
import com.prgrms.bdbks.domain.payment.service.PaymentService;
import com.prgrms.bdbks.domain.star.service.StarService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentFacadeService {
	//TODO 파사드 테스트, 결제 취소
	private final PaymentService paymentService;
	private final CardService cardService;

	@Transactional
	public void orderPay(Order order, PaymentOrderRequest paymentOrderRequest) {

		cardService.pay(paymentOrderRequest.getUserId(), paymentOrderRequest.getCardId(),
			paymentOrderRequest.getTotalPrice());

		paymentService.orderPay(order, paymentOrderRequest.getCardId(), paymentOrderRequest.getTotalPrice());

	}

	@Transactional
	public void chargePay(Long userId, PaymentChargeRequest paymentChargeRequest) {
		cardService.charge(userId,
			paymentChargeRequest.getCardId(),
			paymentChargeRequest.getAmount());

		paymentService.chargePay(paymentChargeRequest.getCardId(), paymentChargeRequest.getAmount());

	}

}
