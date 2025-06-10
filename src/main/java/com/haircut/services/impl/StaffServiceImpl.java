package com.haircut.services.impl;

import com.haircut.dao.StaffDAO;
import com.haircut.models.Staff;
import com.haircut.services.StaffService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDAO staffDAO;

    @Override
    public ApiResponse<Staff> saveStaff(Staff staff) {
        try {
            // Validate input
            if (staff == null) {
                return ApiResponse.error("Dữ liệu nhân viên không được để trống");
            }
            if (!StringUtils.hasText(staff.getFullName())) {
                return ApiResponse.error("Tên nhân viên không được để trống");
            }
            if (!StringUtils.hasText(staff.getPhoneNumber())) {
                return ApiResponse.error("Số điện thoại không được để trống");
            }
            if (!StringUtils.hasText(staff.getEmail())) {
                return ApiResponse.error("Email không được để trống");
            }
            if (!StringUtils.hasText(staff.getPosition())) {
                return ApiResponse.error("Vị trí không được để trống");
            }

            // Check if phone number already exists
            if (staffDAO.existsByPhoneNumber(staff.getPhoneNumber())) {
                return ApiResponse.error("Số điện thoại đã tồn tại");
            }

            // Check if email already exists
            if (staffDAO.existsByEmail(staff.getEmail())) {
                return ApiResponse.error("Email đã tồn tại");
            }

            Staff savedStaff = staffDAO.save(staff);
            return ApiResponse.success("Tạo nhân viên thành công", savedStaff);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tạo nhân viên: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Staff> getStaffById(Long id) {
        try {
            if (id == null) {
                return ApiResponse.error("ID nhân viên không được để trống");
            }
            Staff staff = staffDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
            return ApiResponse.success("Lấy thông tin nhân viên thành công", staff);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy thông tin nhân viên: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> getStaffByPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return Optional.empty();
        }
        return staffDAO.findByPhoneNumber(phone);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> getStaffByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return Optional.empty();
        }
        return staffDAO.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Staff>> getAllStaff() {
        try {
            List<Staff> staffList = staffDAO.findAll();
            if (staffList.isEmpty()) {
                return ApiResponse.success("Không có nhân viên nào", staffList);
            }
            return ApiResponse.success("Lấy danh sách nhân viên thành công", staffList);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Staff>> getActiveStaff() {
        try {
            List<Staff> staffList = staffDAO.findAll().stream()
                .filter(Staff::isActive)
                .collect(Collectors.toList());
            if (staffList.isEmpty()) {
                return ApiResponse.success("Không có nhân viên đang hoạt động", staffList);
            }
            return ApiResponse.success("Lấy danh sách nhân viên đang hoạt động thành công", staffList);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách nhân viên đang hoạt động: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Staff>> getStaffByPosition(String position) {
        try {
            if (!StringUtils.hasText(position)) {
                return ApiResponse.error("Vị trí không được để trống");
            }
            List<Staff> staffList = staffDAO.findAll().stream()
                .filter(staff -> position.equals(staff.getPosition()))
                .collect(Collectors.toList());
            if (staffList.isEmpty()) {
                return ApiResponse.success("Không có nhân viên nào ở vị trí này", staffList);
            }
            return ApiResponse.success("Lấy danh sách nhân viên theo vị trí thành công", staffList);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách nhân viên theo vị trí: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Staff>> searchStaffByFullName(String fullName) {
        try {
            if (!StringUtils.hasText(fullName)) {
                return ApiResponse.error("Tên tìm kiếm không được để trống");
            }
            List<Staff> staffList = staffDAO.findByFullNameContainingIgnoreCase(fullName);
            if (staffList.isEmpty()) {
                return ApiResponse.success("Không tìm thấy nhân viên nào", staffList);
            }
            return ApiResponse.success("Tìm kiếm nhân viên thành công", staffList);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Staff> updateStaff(Long id, Staff staffDetails) {
        try {
            if (id == null) {
                return ApiResponse.error("ID nhân viên không được để trống");
            }
            if (staffDetails == null) {
                return ApiResponse.error("Dữ liệu cập nhật không được để trống");
            }

            Staff staff = staffDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

            // Check if new phone number exists for other staff
            if (StringUtils.hasText(staffDetails.getPhoneNumber()) && 
                !staffDetails.getPhoneNumber().equals(staff.getPhoneNumber())) {
                Optional<Staff> existingStaff = staffDAO.findByPhoneNumber(staffDetails.getPhoneNumber());
                if (existingStaff.isPresent() && !existingStaff.get().getId().equals(id)) {
                    return ApiResponse.error("Số điện thoại đã tồn tại");
                }
            }

            // Check if new email exists for other staff
            if (StringUtils.hasText(staffDetails.getEmail()) && 
                !staffDetails.getEmail().equals(staff.getEmail())) {
                Optional<Staff> existingStaff = staffDAO.findByEmail(staffDetails.getEmail());
                if (existingStaff.isPresent() && !existingStaff.get().getId().equals(id)) {
                    return ApiResponse.error("Email đã tồn tại");
                }
            }

            // Update staff information
            if (StringUtils.hasText(staffDetails.getFullName())) {
                staff.setFullName(staffDetails.getFullName());
            }
            if (StringUtils.hasText(staffDetails.getPhoneNumber())) {
                staff.setPhoneNumber(staffDetails.getPhoneNumber());
            }
            if (StringUtils.hasText(staffDetails.getEmail())) {
                staff.setEmail(staffDetails.getEmail());
            }
            if (StringUtils.hasText(staffDetails.getPosition())) {
                staff.setPosition(staffDetails.getPosition());
            }
            staff.setActive(staffDetails.isActive());

            Staff updatedStaff = staffDAO.save(staff);
            return ApiResponse.success("Cập nhật nhân viên thành công", updatedStaff);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deleteStaff(Long id) {
        try {
            if (id == null) {
                return ApiResponse.error("ID nhân viên không được để trống");
            }
            if (!staffDAO.existsById(id)) {
                return ApiResponse.error("Không tìm thấy nhân viên");
            }
            staffDAO.deleteById(id);
            return ApiResponse.success("Xóa nhân viên thành công", null);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deactivateStaff(Long id) {
        try {
            if (id == null) {
                return ApiResponse.error("ID nhân viên không được để trống");
            }
            Staff staff = staffDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
            
            if (!staff.isActive()) {
                return ApiResponse.error("Nhân viên đã bị vô hiệu hóa trước đó");
            }
            
            staff.setActive(false);
            staffDAO.save(staff);
            return ApiResponse.success("Vô hiệu hóa nhân viên thành công", null);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi vô hiệu hóa nhân viên: " + e.getMessage());
        }
    }
} 