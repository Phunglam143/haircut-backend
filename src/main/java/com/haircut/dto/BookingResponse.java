package com.haircut.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BookingResponse {
    private int id;
    private String customerName;
    private String phone;
    private String service;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    private String message;
} 