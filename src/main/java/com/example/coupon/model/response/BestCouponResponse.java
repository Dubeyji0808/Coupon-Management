package com.example.coupon.model.response;

import com.example.coupon.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestCouponResponse {
    private Coupon coupon;
    private double discount;
}
