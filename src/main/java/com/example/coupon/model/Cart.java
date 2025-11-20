package com.example.coupon.model;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
}
