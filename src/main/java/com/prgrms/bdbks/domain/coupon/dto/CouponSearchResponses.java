package com.prgrms.bdbks.domain.coupon.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CouponSearchResponses {

    private List<CouponSearchResponse> couponSearchResponses;

    private CouponSearchResponses(List<CouponSearchResponse> couponSearchResponses) {
        this.couponSearchResponses = couponSearchResponses;
    }

    public static CouponSearchResponses of(List<CouponSearchResponse> couponSearchResponses) {
        return new CouponSearchResponses(couponSearchResponses);
    }
}
