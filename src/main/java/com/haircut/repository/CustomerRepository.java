package com.haircut.repository;

import com.haircut.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    List<Customer> findByActive(boolean active);
    List<Customer> findByFullNameContainingIgnoreCase(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
} 