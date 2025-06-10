package com.haircut.services;

import com.haircut.models.Customer;
import com.haircut.utils.ApiResponse;
import java.util.List;

public interface CustomerService {
    ApiResponse<Customer> createCustomer(Customer customer);
    ApiResponse<Customer> getCustomerById(Long id);
    ApiResponse<Customer> getCustomerByPhone(String phone);
    ApiResponse<Customer> getCustomerByEmail(String email);
    ApiResponse<List<Customer>> getAllCustomers();
    ApiResponse<List<Customer>> searchCustomersByName(String name);
    ApiResponse<Customer> updateCustomer(Long id, Customer customerDetails);
    ApiResponse<Void> deleteCustomer(Long id);
} 