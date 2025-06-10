package com.haircut.services.impl;

import com.haircut.dao.CustomerDAO;
import com.haircut.models.Customer;
import com.haircut.services.CustomerService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public ApiResponse<Customer> createCustomer(Customer customer) {
        try {
            // Validate input
            if (customer == null) {
                return ApiResponse.error("Dữ liệu khách hàng không được để trống");
            }
            if (!StringUtils.hasText(customer.getFullName())) {
                return ApiResponse.error("Tên khách hàng không được để trống");
            }
            if (!StringUtils.hasText(customer.getPhoneNumber())) {
                return ApiResponse.error("Số điện thoại không được để trống");
            }
            if (!StringUtils.hasText(customer.getEmail())) {
                return ApiResponse.error("Email không được để trống");
            }

            // Check if phone number already exists
            if (customerDAO.findByPhoneNumber(customer.getPhoneNumber()).isPresent()) {
                return ApiResponse.error("Số điện thoại đã tồn tại");
            }

            // Check if email already exists
            if (customerDAO.findByEmail(customer.getEmail()).isPresent()) {
                return ApiResponse.error("Email đã tồn tại");
            }

            Customer savedCustomer = customerDAO.save(customer);
            return ApiResponse.success("Tạo khách hàng thành công", savedCustomer);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tạo khách hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Customer> getCustomerById(Long id) {
        try {
            if (id == null) {
                return ApiResponse.error("ID khách hàng không được để trống");
            }
            Customer customer = customerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            return ApiResponse.success("Lấy thông tin khách hàng thành công", customer);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Customer> getCustomerByPhone(String phoneNumber) {
        try {
            if (!StringUtils.hasText(phoneNumber)) {
                return ApiResponse.error("Số điện thoại không được để trống");
            }
            Customer customer = customerDAO.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            return ApiResponse.success("Lấy thông tin khách hàng thành công", customer);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<Customer> getCustomerByEmail(String email) {
        try {
            if (!StringUtils.hasText(email)) {
                return ApiResponse.error("Email không được để trống");
            }
            Customer customer = customerDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            return ApiResponse.success("Lấy thông tin khách hàng thành công", customer);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerDAO.findAll();
            if (customers.isEmpty()) {
                return ApiResponse.success("Không có khách hàng nào", customers);
            }
            return ApiResponse.success("Lấy danh sách khách hàng thành công", customers);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<Customer>> searchCustomersByName(String name) {
        try {
            if (!StringUtils.hasText(name)) {
                return ApiResponse.error("Tên tìm kiếm không được để trống");
            }
            List<Customer> customers = customerDAO.findByFullNameContaining(name);
            if (customers.isEmpty()) {
                return ApiResponse.success("Không tìm thấy khách hàng nào", customers);
            }
            return ApiResponse.success("Tìm kiếm khách hàng thành công", customers);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Customer> updateCustomer(Long id, Customer customerDetails) {
        try {
            if (id == null) {
                return ApiResponse.error("ID khách hàng không được để trống");
            }
            if (customerDetails == null) {
                return ApiResponse.error("Dữ liệu cập nhật không được để trống");
            }

            Customer customer = customerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

            // Check if new phone number exists for other customers
            if (StringUtils.hasText(customerDetails.getPhoneNumber()) && 
                !customerDetails.getPhoneNumber().equals(customer.getPhoneNumber())) {
                if (customerDAO.findByPhoneNumber(customerDetails.getPhoneNumber()).isPresent()) {
                    return ApiResponse.error("Số điện thoại đã tồn tại");
                }
            }

            // Check if new email exists for other customers
            if (StringUtils.hasText(customerDetails.getEmail()) && 
                !customerDetails.getEmail().equals(customer.getEmail())) {
                if (customerDAO.findByEmail(customerDetails.getEmail()).isPresent()) {
                    return ApiResponse.error("Email đã tồn tại");
                }
            }

            // Update customer information
            if (StringUtils.hasText(customerDetails.getFullName())) {
                customer.setFullName(customerDetails.getFullName());
            }
            if (StringUtils.hasText(customerDetails.getPhoneNumber())) {
                customer.setPhoneNumber(customerDetails.getPhoneNumber());
            }
            if (StringUtils.hasText(customerDetails.getEmail())) {
                customer.setEmail(customerDetails.getEmail());
            }
            customer.setActive(customerDetails.isActive());

            Customer updatedCustomer = customerDAO.save(customer);
            return ApiResponse.success("Cập nhật khách hàng thành công", updatedCustomer);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi cập nhật khách hàng: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deleteCustomer(Long id) {
        try {
            if (id == null) {
                return ApiResponse.error("ID khách hàng không được để trống");
            }
            if (!customerDAO.existsById(id)) {
                return ApiResponse.error("Không tìm thấy khách hàng");
            }
            customerDAO.deleteById(id);
            return ApiResponse.success("Xóa khách hàng thành công", null);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
    }
} 