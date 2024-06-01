package com.example.auuthenticationsystem.userAccount;

import com.example.auuthenticationsystem.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserAccount {
    // Map to store user accounts, where the key is the username and the value is a User object
    private final Map<String, User> accounts;

    public UserAccount() {
        accounts = new HashMap<>();
    }

    // Method to add a new user account
    public void addUser(String username, User user) {
        accounts.put(username, user);
    }

    // Method to retrieve a user account by username
    public User getUser(String username) {
        return accounts.get(username);
    }

    // Method to check if a user account exists
    public boolean containsUser(String username) {
        return accounts.containsKey(username);
    }

    // Other methods for managing user accounts, such as updating balances, verifying users, etc.
    // You can add methods as needed based on your requirements

    // Example method to update user balance
    public void updateBalance(String username, int newBalance) {
        if (accounts.containsKey(username)) {
            User user = accounts.get(username);
            assert user != null;
            user.setBalance(newBalance);
            accounts.put(username, user);
        } else {
            System.out.println("User not found");
        }
    }
}

