package com.example.auuthenticationsystem.user;

import androidx.annotation.NonNull;

public class User {
    private String username;
    private int balance; // Assuming balance is represented as an integer

    public User(String username, int balance) {
        this.username = username;
        this.balance = balance;
    }


    public User(String username) {
    }

    // Getter and setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // Getter and setter methods for balance
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    // Method to add Blue Coins to the balance
    public void deposit(int amount) {
        balance += amount;
    }

    // Method to subtract Blue Coins from the balance
    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}

