package com.example.coupon.controller;

import com.example.coupon.model.Coupon;
import com.example.coupon.model.request.BestCouponRequest;
import com.example.coupon.model.request.CreateCouponRequest;
import com.example.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // POST /coupons
    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody CreateCouponRequest request) {
        Coupon created = couponService.createCoupon(request);
        return ResponseEntity.ok(created);
    }

    // OPTIONAL: GET /coupons
    @GetMapping
    public ResponseEntity<Object> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @PostMapping("/best")
    public ResponseEntity<?> getBestCoupon(@RequestBody BestCouponRequest request) {
        return ResponseEntity.ok(couponService.findBestCoupon(request));
    }

}
