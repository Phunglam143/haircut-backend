package com.haircut.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private String service; // Tên dịch vụ (ví dụ: "coloring")
    private String datetime; // Chuỗi thời gian từ frontend (ví dụ: "2025-06-06T05:32:00.000Z")
    private String name;
    private String phone;
    private String email;
    private String notes;
} 