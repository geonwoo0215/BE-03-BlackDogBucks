package com.prgrms.bdbks.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.bdbks.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
