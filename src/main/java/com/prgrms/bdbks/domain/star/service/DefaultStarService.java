package com.prgrms.bdbks.domain.star.service;

import javax.xml.stream.events.StartDocument;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.bdbks.common.exception.EntityNotFoundException;
import com.prgrms.bdbks.domain.star.entity.Star;
import com.prgrms.bdbks.domain.star.mapper.StarMapper;
import com.prgrms.bdbks.domain.star.repository.StarRepository;
import com.prgrms.bdbks.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultStarService implements StarService {

	private static final short ZERO = 0;

	private final StarRepository starRepository;
	private final StarMapper starMapper;

	@Override
	@Transactional
	public Long create(User user) {
		Star star = Star.builder()
			.user(user)
			.count(ZERO)
			.build();

		starRepository.save(star);
		return star.getId();
	}

	@Override
	public Star findByUserId(Long userId) {
		Star star = starRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException(Star.class, userId));

		return star;
	}

	@Override
	@Transactional
	public void delete(Long userId) {
		starRepository.deleteByUserId(userId);
	}

	@Override
	public void updateCount(Long userId, int orderCount, boolean isCouponUsed) {
		Star star =  starRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException(Star.class, userId));

		if (!isCouponUsed) {
			star.updateCount(orderCount);
		}
	}

	//TODO 거래 취소, 반품 시 별은 원상복구(원래 상태로 감소)

	@Override
	public int checkCountAndExchangeCoupon() {
		return 0;
	}
}