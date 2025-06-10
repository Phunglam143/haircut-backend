package com.haircut.services;

import com.haircut.models.Booking;
import com.haircut.utils.ApiResponse;
import com.haircut.dto.BookingUpdateRequest;
import java.util.List;

public interface BookingService {
    ApiResponse<Booking> createBooking(Booking booking);
    ApiResponse<Booking> getBookingById(Long id);
    ApiResponse<List<Booking>> getAllBookings();
    ApiResponse<List<Booking>> getBookingsByDate(String date);
    ApiResponse<List<Booking>> getBookingsByCustomer(Long customerId);
    ApiResponse<List<Booking>> getBookingsByStaff(Long staffId);
    ApiResponse<List<Booking>> getBookingsByStatus(Booking.BookingStatus status);
    ApiResponse<Booking> updateBooking(Long id, BookingUpdateRequest bookingUpdateRequest);
    ApiResponse<Void> deleteBooking(Long id);
    ApiResponse<Booking> updateBookingStatus(Long id, Booking.BookingStatus newStatus);
    ApiResponse<List<Booking>> getPendingBookings();
} 