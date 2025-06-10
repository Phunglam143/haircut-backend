package com.haircut.services.impl;

import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import com.haircut.models.HaircutService;
import com.haircut.models.Customer;
import com.haircut.repository.BookingRepository;
import com.haircut.services.BookingService;
import com.haircut.services.CustomerService;
import com.haircut.services.ServiceService;
import com.haircut.utils.ApiResponse;
import com.haircut.dto.BookingUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ServiceService serviceService;

    @Override
    public ApiResponse<Booking> createBooking(Booking booking) {
        try {
            Booking savedBooking = bookingRepository.save(booking);
            return ApiResponse.success(savedBooking);
        } catch (Exception e) {
            return ApiResponse.error("Failed to create booking: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Booking> getBookingById(Long id) {
        try {
            Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            return ApiResponse.success(booking);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get booking: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            return ApiResponse.success(bookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get bookings: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getBookingsByDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Booking> bookings = bookingRepository.findByBookingDate(localDate);
            return ApiResponse.success(bookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get bookings by date: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getBookingsByCustomer(Long customerId) {
        try {
            List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
            return ApiResponse.success(bookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get bookings by customer: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getBookingsByStaff(Long staffId) {
        try {
            List<Booking> bookings = bookingRepository.findByStaffId(staffId);
            return ApiResponse.success(bookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get bookings by staff: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getBookingsByStatus(Booking.BookingStatus status) {
        try {
            List<Booking> bookings = bookingRepository.findByStatus(status);
            return ApiResponse.success(bookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to get bookings by status: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Booking> updateBooking(Long id, BookingUpdateRequest bookingUpdateRequest) {
        try {
            Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

            if (bookingUpdateRequest.getServiceName() != null && !bookingUpdateRequest.getServiceName().isEmpty()) {
                ApiResponse<List<HaircutService>> serviceResponse = serviceService.searchServicesByName(bookingUpdateRequest.getServiceName());
                if (serviceResponse.isSuccess() && !serviceResponse.getData().isEmpty()) {
                    booking.setService(serviceResponse.getData().get(0));
                } else {
                    throw new RuntimeException("Service not found with name: " + bookingUpdateRequest.getServiceName());
                }
            }

            if (bookingUpdateRequest.getCustomerId() != null) {
                ApiResponse<Customer> customerResponse = customerService.getCustomerById(bookingUpdateRequest.getCustomerId());
                if (customerResponse.isSuccess() && customerResponse.getData() != null) {
                    booking.setCustomer(customerResponse.getData());
                } else {
                    throw new RuntimeException("Customer not found with ID: " + bookingUpdateRequest.getCustomerId());
                }
            }

            if (bookingUpdateRequest.getBookingDate() != null) {
                System.out.println("DEBUG: BookingUpdateRequest date: " + bookingUpdateRequest.getBookingDate());
                booking.setBookingDate(LocalDate.parse(bookingUpdateRequest.getBookingDate()));
                System.out.println("DEBUG: Booking object date after parsing: " + booking.getBookingDate());
            }
            if (bookingUpdateRequest.getBookingTime() != null) {
                System.out.println("DEBUG: BookingUpdateRequest time: " + bookingUpdateRequest.getBookingTime());
                booking.setBookingTime(LocalTime.parse(bookingUpdateRequest.getBookingTime()));
                System.out.println("DEBUG: Booking object time after parsing: " + booking.getBookingTime());
            }

            if (bookingUpdateRequest.getNotes() != null) {
                booking.setNotes(bookingUpdateRequest.getNotes());
            }

            if (bookingUpdateRequest.getStatus() != null && !bookingUpdateRequest.getStatus().isEmpty()) {
                booking.setStatus(BookingStatus.valueOf(bookingUpdateRequest.getStatus().toUpperCase()));
            }

            Booking updatedBooking = bookingRepository.save(booking);
            System.out.println("DEBUG: Updated Booking saved date: " + updatedBooking.getBookingDate());
            System.out.println("DEBUG: Updated Booking saved time: " + updatedBooking.getBookingTime());
            return ApiResponse.success("Booking updated successfully", updatedBooking);
        } catch (Exception e) {
            return ApiResponse.error("Failed to update booking: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deleteBooking(Long id) {
        try {
            bookingRepository.deleteById(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error("Failed to delete booking: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Booking> updateBookingStatus(Long id, Booking.BookingStatus newStatus) {
        try {
            Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
            
            booking.setStatus(newStatus);
            Booking updatedBooking = bookingRepository.save(booking);
            return ApiResponse.success("Booking status updated successfully", updatedBooking);
        } catch (Exception e) {
            return ApiResponse.error("Failed to update booking status: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Booking>> getPendingBookings() {
        try {
            List<Booking> pendingBookings = bookingRepository.findByStatus(BookingStatus.PENDING);
            return ApiResponse.success("Fetched pending bookings successfully", pendingBookings);
        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch pending bookings: " + e.getMessage());
        }
    }
} 