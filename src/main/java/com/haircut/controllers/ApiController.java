package com.haircut.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.haircut.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = "*")
public class ApiController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getApiInfo() {
        return ResponseEntity.ok(ApiResponse.success("Haircut Booking API", "API đang hoạt động bình thường"));
    }
} 