package com.haircut.repository;

import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Tìm kiếm theo ngày
    List<Booking> findByBookingDate(LocalDate date);
    
    // Tìm kiếm theo khách hàng
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByCustomerIdAndStatus(Long customerId, BookingStatus status);
    
    // Tìm kiếm theo nhân viên
    List<Booking> findByStaffId(Long staffId);
    
    // Tìm kiếm theo dịch vụ
    List<Booking> findByServiceId(Long serviceId);
    
    // Tìm kiếm theo trạng thái
    List<Booking> findByStatus(BookingStatus status);
    
    // Tìm kiếm theo khoảng thời gian
    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);
    
    // Kiểm tra trùng lặp
    boolean existsByBookingTimeBetween(LocalDateTime start, LocalDateTime end);
} 