package com.haircut.dao;

import com.haircut.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffDAO extends JpaRepository<Staff, Long> {
    Optional<Staff> findByPhoneNumber(String phoneNumber);
    Optional<Staff> findByEmail(String email);
    List<Staff> findByActive(boolean active);
    List<Staff> findByPosition(String position);
    List<Staff> findByFullNameContainingIgnoreCase(String fullName);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
} 