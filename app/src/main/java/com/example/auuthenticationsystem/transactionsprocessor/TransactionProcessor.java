package com.example.auuthenticationsystem.transactionsprocessor;

import com.example.auuthenticationsystem.bank.BlueBank;
import com.example.auuthenticationsystem.transaction.Transaction;

public class TransactionProcessor {
    private final BlueBank blueBank;

    public TransactionProcessor(BlueBank blueBank) {
        this.blueBank = blueBank;
    }

    public void processTransaction(Transaction transaction) {
        // Validate the transaction
        if (isValidTransaction(transaction)) {
            // Process the transaction
            switch (transaction.getTransactionType()) {
                case "Deposit":
                    blueBank.deposit(transaction.getSender(), transaction.getAmount());
                    break;
                case "Withdrawal":
                    blueBank.withdraw(transaction.getSender(), transaction.getAmount());
                    break;
                case "Transfer":
                    String[] parts = transaction.getReceiver().split(" to ");
                    String sender = parts[0];
                    String receiver = parts[1];
                    blueBank.transfer(sender, receiver, transaction.getAmount());
                    break;
                default:
                    // Unknown transaction type
                    break;
            }
        } else {
            // Invalid transaction
            System.out.println("Invalid transaction: " + transaction);
        }
    }

    private boolean isValidTransaction(Transaction transaction) {
        // Perform validation logic here
        // For example, check if the sender has sufficient balance for withdrawal
        if (transaction.getTransactionType().equals("Withdrawal")) {
            int balance = blueBank.getBalance(transaction.getSender());
            return balance >= transaction.getAmount();
        }
        // Other validation checks can be added based on requirements
        return true;
    }
}

