package com.prgrms.bdbks.domain.coupon.service;

import com.prgrms.bdbks.domain.coupon.dto.CouponSaveResponse;
import com.prgrms.bdbks.domain.coupon.dto.CouponSearchResponses;
import com.prgrms.bdbks.domain.coupon.entity.Coupon;
import com.prgrms.bdbks.domain.coupon.repository.CouponRepository;
import com.prgrms.bdbks.domain.coupon.service.mapper.CouponMapper;
import com.prgrms.bdbks.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;

    public CouponSaveResponse create(User user) {

        Coupon coupon = Coupon.builder()
                .userId(user.getId())
                .name(user.getNickname() + "의 쿠폰")
                .price(6000)
                .expireDate(LocalDateTime.now().plusMonths(6L))
                .build();

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
