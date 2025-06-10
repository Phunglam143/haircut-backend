package com.haircut.model;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private String customerName;
    private String phoneNumber;
    private String serviceName;
    private LocalDateTime bookingTime;
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    private String notes;

    // Constructors
    public Booking() {}

    public Booking(Long id, String customerName, String phoneNumber, String serviceName, 
                  LocalDateTime bookingTime, String status, String notes) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.serviceName = serviceName;
        this.bookingTime = bookingTime;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 