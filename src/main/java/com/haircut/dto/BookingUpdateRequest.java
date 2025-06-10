package com.haircut.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateRequest {
    private Long id;
    private String serviceName;
    private Long customerId;
    private String bookingDate;
    private String bookingTime;
    private String notes;
    private String status; // Allow updating status if needed
} 