package com.user.lms.entity;

public enum BookingStatus {
    PROVIDER_PENDING("Provider Pending"),
    CUSTOMER_PENDING("Customer Pending"),
    PARTIAL_PAYMENT_PENDING("Partial Payment Pending"),
    PARTIAL_PAYMENT_SEND_BY_CUSTOMER("Partial Payment Send By Customer"),
    PARTIAL_PAYMENT_RECEIVED("Partial Payment Received"),
    FULL_PAYMENT_PENDING("Full Payment Pending"),
    PENDING_PICKUP("Pending PickUp"),
    COMPLETED("Completed"),
    CANCEL("Cancel");
    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
