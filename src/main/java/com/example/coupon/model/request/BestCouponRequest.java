package com.example.coupon.model.request;

import com.example.coupon.model.Cart;
import com.example.coupon.model.UserContext;
import lombok.Data;

@Data
public class BestCouponRequest {
    private UserContext user;
    private Cart cart;
}
