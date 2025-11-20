package com.example.coupon.store;

import com.example.coupon.model.Coupon;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CouponStore {

    private final Map<String, Coupon> coupons = new ConcurrentHashMap<>();

    public void save(Coupon coupon) {
        coupons.put(coupon.getCode(), coupon);
    }

    public Coupon get(String code) {
        return coupons.get(code);
    }

    public Map<String, Coupon> getAll() {
        return coupons;
    }

    public boolean exists(String code) {
        return coupons.containsKey(code);
    }
}
