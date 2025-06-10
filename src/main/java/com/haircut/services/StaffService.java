package com.haircut.services;

import com.haircut.models.Staff;
import com.haircut.utils.ApiResponse;
import java.util.List;
import java.util.Optional;

public interface StaffService {
    ApiResponse<Staff> saveStaff(Staff staff);
    ApiResponse<Staff> getStaffById(Long id);
    Optional<Staff> getStaffByPhone(String phone);
    Optional<Staff> getStaffByEmail(String email);
    ApiResponse<List<Staff>> getAllStaff();
    ApiResponse<List<Staff>> getActiveStaff();
    ApiResponse<List<Staff>> getStaffByPosition(String position);
    ApiResponse<List<Staff>> searchStaffByFullName(String fullName);
    ApiResponse<Staff> updateStaff(Long id, Staff staffDetails);
    ApiResponse<Void> deleteStaff(Long id);
    ApiResponse<Void> deactivateStaff(Long id);
} 