package com.haircut.controllers;

import com.haircut.models.Staff;
import com.haircut.services.StaffService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin(originPatterns = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<ApiResponse<Staff>> createStaff(@RequestBody Staff staff) {
        ApiResponse<Staff> response = staffService.saveStaff(staff);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Staff>> updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        ApiResponse<Staff> response = staffService.updateStaff(id, staffDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStaff(@PathVariable Long id) {
        ApiResponse<Void> response = staffService.deleteStaff(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Staff>>> getAllStaff() {
        ApiResponse<List<Staff>> response = staffService.getAllStaff();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<Staff>>> getStaffByPosition(@PathVariable String position) {
        ApiResponse<List<Staff>> response = staffService.getStaffByPosition(position);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponse<Staff>> getStaffByPhone(@PathVariable String phone) {
        try {
            Optional<Staff> staff = staffService.getStaffByPhone(phone);
            return staff.map(s -> ResponseEntity.ok(ApiResponse.success("Lấy thông tin nhân viên thành công", s)))
                    .orElseGet(() -> ResponseEntity.ok(ApiResponse.error("Không tìm thấy nhân viên")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi lấy nhân viên theo số điện thoại: " + e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Staff>> getStaffByEmail(@PathVariable String email) {
        try {
            Optional<Staff> staff = staffService.getStaffByEmail(email);
            return staff.map(s -> ResponseEntity.ok(ApiResponse.success("Lấy thông tin nhân viên thành công", s)))
                    .orElseGet(() -> ResponseEntity.ok(ApiResponse.error("Không tìm thấy nhân viên")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi lấy nhân viên theo email: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Staff>> getStaffById(@PathVariable Long id) {
        ApiResponse<Staff> response = staffService.getStaffById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Staff>>> getActiveStaff() {
        ApiResponse<List<Staff>> response = staffService.getActiveStaff();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Staff>>> searchStaffByFullName(@RequestParam String fullName) {
        ApiResponse<List<Staff>> response = staffService.searchStaffByFullName(fullName);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateStaff(@PathVariable Long id) {
        try {
            ApiResponse<Void> response = staffService.deactivateStaff(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi khi vô hiệu hóa nhân viên: " + e.getMessage()));
        }
    }
} 