package com.haircut.dao;

import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);
    Optional<Booking> findById(Long id);
    List<Booking> findAll();
    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findByStaffId(Long staffId);
    List<Booking> findByServiceId(Long serviceId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Booking> findByCustomerIdAndStatus(Long customerId, BookingStatus status);
    void deleteById(Long id);
    void delete(Booking booking);
    boolean existsById(Long id);
    boolean existsByTimeSlot(LocalDateTime start, LocalDateTime end);
} 