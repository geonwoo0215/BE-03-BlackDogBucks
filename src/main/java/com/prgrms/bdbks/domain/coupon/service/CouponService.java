package com.prgrms.bdbks.domain.coupon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prgrms.bdbks.domain.coupon.dto.CouponSaveResponse;
import com.prgrms.bdbks.domain.coupon.dto.CouponSearchResponses;
import com.prgrms.bdbks.domain.coupon.entity.Coupon;
import com.prgrms.bdbks.domain.coupon.mapper.CouponMapper;
import com.prgrms.bdbks.domain.coupon.repository.CouponRepository;
import com.prgrms.bdbks.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
	public static final int PRICE_CONDITION = 50000;
	private final CouponMapper couponMapper;
	private final CouponRepository couponRepository;

	public CouponSaveResponse create(Long userId) {

		Coupon coupon = Coupon.createCoupon(userId);

		Coupon saveCoupon = couponRepository.save(coupon);

		return couponMapper.toCouponSaveResponse(saveCoupon);
	}

	public CouponSearchResponses getCouponList(User user) {

		List<Coupon> coupons = couponRepository.findByUserId(user.getId());

		return CouponSearchResponses.of(
			coupons.stream()
				.map(couponMapper::toCouponSearchResponse)
				.collect(Collectors.toList()));
	}
}
