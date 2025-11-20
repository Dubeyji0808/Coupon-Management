package com.example.coupon.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Coupon {
    private String code;
    private String description;

    private String discountType; // FLAT or PERCENT
    private double discountValue;
    private Double maxDiscountAmount; // optional

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer usageLimitPerUser; // optional

    private Eligibility eligibility;
}
