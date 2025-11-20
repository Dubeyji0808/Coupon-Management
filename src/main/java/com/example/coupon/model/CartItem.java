package com.example.coupon.model;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private String category;
    private double unitPrice;
    private int quantity;
}
