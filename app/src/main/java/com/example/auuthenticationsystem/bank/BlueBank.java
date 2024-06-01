package com.example.auuthenticationsystem.bank;
import com.example.auuthenticationsystem.transaction.Transaction;
import com.example.auuthenticationsystem.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueBank {
    private final Map<String, User> users;
    private final List<Transaction> transactions;

    public BlueBank() {
        this.users = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    public void registerUser(String username) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username));
        }
    }

    public void deposit(String username, int amount) {
        User user = users.get(username);
        if (user != null) {
            user.deposit(amount);
            recordTransaction(new Transaction("Deposit", username, amount));
        }
    }

    public void withdraw(String username, int amount) {
        User user = users.get(username);
        if (user != null && user.getBalance() >= amount) {
            user.withdraw(amount);
            recordTransaction(new Transaction("Withdrawal", username, amount));
        }
    }

    public void transfer(String sender, String receiver, int amount) {
        User senderUser = users.get(sender);
        User receiverUser = users.get(receiver);
        if (senderUser != null && receiverUser != null && senderUser.getBalance() >= amount) {
            senderUser.withdraw(amount);
            receiverUser.deposit(amount);
            recordTransaction(new Transaction("Transfer", sender + " to " + receiver, amount));
        }
    }

    private void recordTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public int getBalance(String username) {
        User user = users.get(username);
        return user != null ? user.getBalance() : 0;
    }
}

