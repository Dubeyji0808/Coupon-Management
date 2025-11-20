package com.example.coupon.model.request;

import com.example.coupon.model.Eligibility;
import lombok.Data;

@Data
public class CreateCouponRequest {
    private String code;
    private String description;

    private String discountType;
    private double discountValue;
    private Double maxDiscountAmount;

    private String startDate; // incoming string, will convert to LocalDateTime
    private String endDate;

    private Integer usageLimitPerUser;

    private Eligibility eligibility;
}
