package com.example.auuthenticationsystem.coin;

import java.util.UUID;

public class Coin {
    private final String coinId;
    private static int totalSupply;

    public Coin() {
        this.coinId = generateCoinId();
        totalSupply++;
    }

    private String generateCoinId() {
        // Generate a unique coin ID using UUID
        return UUID.randomUUID().toString();
    }

    public static int getTotalSupply() {
        return totalSupply;
    }

    public String getCoinId() {
        return coinId;
    }

    // Other methods for managing individual coin instances could be added here
}

