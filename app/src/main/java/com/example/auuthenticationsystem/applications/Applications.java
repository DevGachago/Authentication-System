package com.example.auuthenticationsystem.applications;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Applications {
    private String userId; // User ID to which this application belongs
    private String username;
    private String firstName;
    private String surname;
    private String idNumber;
    private String billingAddress;
    private LocalDateTime timestamp; // Timestamp of when the application was submitted

    // Constructor
    public Applications(String userId, String username, String firstName, String surname,
                       String idNumber, String billingAddress, LocalDateTime timestamp) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.billingAddress = billingAddress;
        this.timestamp = timestamp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Applications(String applicationId, String username, String firstName, String surname, String idNumber, String billingAddress) {
        this.userId = applicationId; // Assuming applicationId is the userId for the application
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
        this.billingAddress = billingAddress;
        this.timestamp = LocalDateTime.now(); // Assigning current timestamp when the application is created
    }


    // Getter and setter methods for each field
    // (Getter and setter for userId are included as well)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Override toString() method for debugging purposes
    @NonNull
    @Override
    public String toString() {
        return "Application{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

