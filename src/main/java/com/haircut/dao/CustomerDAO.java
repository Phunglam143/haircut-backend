package com.haircut.dao;

import com.haircut.models.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    List<Customer> findByActive(boolean active);
    List<Customer> findByFullNameContaining(String name);
    void deleteById(Long id);
    void delete(Customer customer);
    boolean existsById(Long id);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
} 