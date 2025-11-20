package com.example.coupon.model;

import lombok.Data;

@Data
public class UserContext {
    private String userId;
    private String userTier;
    private String country;
    private Integer lifetimeSpend;
    private Integer ordersPlaced;
}
