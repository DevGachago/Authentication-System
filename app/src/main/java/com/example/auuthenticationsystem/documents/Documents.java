package com.example.auuthenticationsystem.documents;

public class Documents {
    private String userId;
    private String documentType;
    private String imageUrl;
    private String timestamp;

    public Documents() {
        // Default constructor required for Firebase
    }

    public Documents(String userId, String documentType, String imageUrl, String timestamp) {
        this.userId = userId;
        this.documentType = documentType;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

