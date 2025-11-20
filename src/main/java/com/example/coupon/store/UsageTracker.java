package com.example.coupon.store;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UsageTracker {

    // userId -> (couponCode -> usageCount)
    private final Map<String, Map<String, Integer>> usageMap = new ConcurrentHashMap<>();

    public int getUsage(String userId, String couponCode) {
        return usageMap
                .getOrDefault(userId, new ConcurrentHashMap<>())
                .getOrDefault(couponCode, 0);
    }

    public void incrementUsage(String userId, String couponCode) {
        usageMap.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<String, Integer> userUsage = usageMap.get(userId);

        userUsage.put(couponCode, userUsage.getOrDefault(couponCode, 0) + 1);
    }
}
