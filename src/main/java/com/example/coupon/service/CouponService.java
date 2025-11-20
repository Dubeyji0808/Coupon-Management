package com.example.coupon.service;

import com.example.coupon.model.Cart;
import com.example.coupon.model.Coupon;
import com.example.coupon.model.Eligibility;
import com.example.coupon.model.UserContext;
import com.example.coupon.model.request.BestCouponRequest;
import com.example.coupon.model.request.CreateCouponRequest;
import com.example.coupon.model.response.BestCouponResponse;
import com.example.coupon.store.CouponStore;
import com.example.coupon.store.UsageTracker;
import com.example.coupon.exception.CouponException;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponService {

    private final CouponStore couponStore;
    private final UsageTracker usageTracker;

    public CouponService(CouponStore couponStore, UsageTracker usageTracker) {
        this.couponStore = couponStore;
        this.usageTracker = usageTracker;
    }

    public Coupon createCoupon(CreateCouponRequest request) {

        // Validate discount type
        if (!request.getDiscountType().equalsIgnoreCase("FLAT")
                && !request.getDiscountType().equalsIgnoreCase("PERCENT")) {
            throw new CouponException("Invalid discountType. Must be FLAT or PERCENT");
        }

        // Block duplicate coupon code
        if (couponStore.exists(request.getCode())) {
            throw new CouponException("Coupon code already exists: " + request.getCode());
        }

        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setDescription(request.getDescription());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setMaxDiscountAmount(request.getMaxDiscountAmount());

        coupon.setStartDate(LocalDateTime.parse(request.getStartDate()));
        coupon.setEndDate(LocalDateTime.parse(request.getEndDate()));

        coupon.setUsageLimitPerUser(request.getUsageLimitPerUser());
        coupon.setEligibility(request.getEligibility());

        couponStore.save(coupon);

        return coupon;
    }

    public Object getAllCoupons() {
        return couponStore.getAll();
    }

    public BestCouponResponse findBestCoupon(BestCouponRequest request) {

        UserContext user = request.getUser();
        Cart cart = request.getCart();
        double cartValue = calculateCartValue(cart);
        int totalItems = calculateItemCount(cart);

        Coupon bestCoupon = null;
        double bestDiscount = 0;

        LocalDateTime now = LocalDateTime.now();

        for (Coupon coupon : couponStore.getAll().values()) {

            // Step 1: Date validity
            if (now.isBefore(coupon.getStartDate()) || now.isAfter(coupon.getEndDate())) {
                continue;
            }

            // Step 2: Usage limit
            int used = usageTracker.getUsage(user.getUserId(), coupon.getCode());
            if (coupon.getUsageLimitPerUser() != null &&
                    used >= coupon.getUsageLimitPerUser()) {
                continue;
            }

            // Step 3: Eligibility
            if (!isCouponEligible(coupon, user, cartValue, totalItems, cart)) {
                continue;
            }

            // Step 4: Discount
            double discount = calculateDiscount(coupon, cartValue);

            // Step 5: Best coupon tie-breaking
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestCoupon = coupon;
            } else if (discount == bestDiscount && bestCoupon != null) {

                if (coupon.getEndDate().isBefore(bestCoupon.getEndDate())) {
                    bestCoupon = coupon;
                } else if (coupon.getEndDate().isEqual(bestCoupon.getEndDate()) &&
                        coupon.getCode().compareTo(bestCoupon.getCode()) < 0) {
                    bestCoupon = coupon;
                }
            }
        }

        // No coupon found
        if (bestCoupon == null) {
            return new BestCouponResponse(null, 0);
        }

        // Increase usage
        usageTracker.incrementUsage(user.getUserId(), bestCoupon.getCode());

        return new BestCouponResponse(bestCoupon, bestDiscount);
    }

    private double calculateDiscount(Coupon coupon, double cartValue) {
        if (coupon.getDiscountType().equalsIgnoreCase("FLAT")) {
            return coupon.getDiscountValue();
        }

        double percentDiscount = (coupon.getDiscountValue() / 100.0) * cartValue;

        if (coupon.getMaxDiscountAmount() != null) {
            return Math.min(percentDiscount, coupon.getMaxDiscountAmount());
        }

        return percentDiscount;
    }

    private boolean isCouponEligible(
            Coupon coupon,
            UserContext user,
            double cartValue,
            int totalItems,
            Cart cart
    ) {
        Eligibility e = coupon.getEligibility();
        if (e == null) return true;

        if (e.getAllowedUserTiers() != null &&
                !e.getAllowedUserTiers().contains(user.getUserTier())) {
            return false;
        }

        if (e.getMinLifetimeSpend() != null &&
                user.getLifetimeSpend() < e.getMinLifetimeSpend()) {
            return false;
        }

        if (e.getMinOrdersPlaced() != null &&
                user.getOrdersPlaced() < e.getMinOrdersPlaced()) {
            return false;
        }

        if (Boolean.TRUE.equals(e.getFirstOrderOnly()) &&
                user.getOrdersPlaced() > 0) {
            return false;
        }

        if (e.getAllowedCountries() != null &&
                !e.getAllowedCountries().contains(user.getCountry())) {
            return false;
        }

        if (e.getMinCartValue() != null &&
                cartValue < e.getMinCartValue()) {
            return false;
        }

        if (e.getMinItemsCount() != null &&
                totalItems < e.getMinItemsCount()) {
            return false;
        }

        if (e.getApplicableCategories() != null &&
                cart.getItems().stream().noneMatch(
                        i -> e.getApplicableCategories().contains(i.getCategory())
                )) {
            return false;
        }

        if (e.getExcludedCategories() != null &&
                cart.getItems().stream().anyMatch(
                        i -> e.getExcludedCategories().contains(i.getCategory())
                )) {
            return false;
        }

        return true;
    }

    private double calculateCartValue(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                .sum();
    }

    private int calculateItemCount(Cart cart) {
        return cart.getItems().stream()
                .mapToInt(i -> i.getQuantity())
                .sum();
    }
}
