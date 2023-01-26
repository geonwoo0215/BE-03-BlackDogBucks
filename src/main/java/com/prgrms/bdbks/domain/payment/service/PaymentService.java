package com.prgrms.bdbks.domain.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.common.exception.EntityNotFoundException;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.order.entity.Order;
import com.prgrms.bdbks.domain.payment.entity.Payment;
import com.prgrms.bdbks.domain.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public String orderPay(Order order, String cardId, int totalPrice) {
		Payment payment = Payment.createOrderPayment(order, cardId, totalPrice);
		paymentRepository.save(payment);

		return payment.getId();
	}

	public String chargePay(String cardId, int totalPrice) {
		Payment payment = Payment.createChargePayment(cardId, totalPrice);
		paymentRepository.save(payment);

		return payment.getId();
	}

	@Transactional
	public void orderPayCancel(String orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
			.orElseThrow(() -> new EntityNotFoundException(Payment.class, orderId));

		payment.changeStatueByOrderCancel();
	}

	//TODO 충전 환불, 결제 취소
	//결제 취소
	//1. 결제 조회
	//2. 결제 상태 변경(APPROVE -> REFUND)
	//3.

	//충전 환불
	//충전 이후 해당 카드를 사용X
}

