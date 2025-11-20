package com.example.coupon.model;

import lombok.Data;
import java.util.List;

@Data
public class Eligibility {
    // User-based rules
    private List<String> allowedUserTiers;
    private Integer minLifetimeSpend;
    private Integer minOrdersPlaced;
    private Boolean firstOrderOnly;
    private List<String> allowedCountries;

    // Cart-based rules
    private Integer minCartValue;
    private List<String> applicableCategories;
    private List<String> excludedCategories;
    private Integer minItemsCount;
}
