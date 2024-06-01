package com.example.auuthenticationsystem.transaction;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String sender;
    private String receiver;
    private int amount;
    private LocalDateTime dateTime;

    private String transactionType;

    public Transaction(String transactionId, String sender, String receiver, int amount, LocalDateTime dateTime, String transactionType) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.dateTime = dateTime;
        this.transactionType = transactionType;
    }

    public Transaction(String deposit, String username, int amount) {

    }

    // Getter and setter methods for transactionId, sender, receiver, amount, and dateTime

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Getter and setter methods for transactionId
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Getter and setter methods for sender
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // Getter and setter methods for receiver
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    // Getter and setter methods for amount
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
