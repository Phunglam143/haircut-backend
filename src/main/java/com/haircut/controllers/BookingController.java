package com.haircut.controllers;

import com.haircut.dto.BookingRequest; // Import DTO
import com.haircut.dto.BookingStatusUpdateRequest; // Import the new DTO
import com.haircut.dto.BookingUpdateRequest; // Import the new DTO
import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import com.haircut.models.Customer; // Import Customer
import com.haircut.models.HaircutService; // Import HaircutService
import com.haircut.repository.BookingRepository;
import com.haircut.services.BookingService; // Import BookingService
import com.haircut.services.CustomerService; // Import CustomerService
import com.haircut.services.ServiceService; // Import ServiceService
import com.haircut.utils.ApiResponse; // Import ApiResponse (Assuming this is used for responses)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/bookings") // Đảm bảo RequestMapping là "/api/bookings"
@CrossOrigin(originPatterns = "*")
public class BookingController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired // Inject ServiceService
    private ServiceService serviceService;

    @Autowired // Inject CustomerService
    private CustomerService customerService;

    @Autowired // Inject BookingService
    private BookingService bookingService; // Inject BookingService

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {
        ApiResponse<List<Booking>> response = bookingService.getAllBookings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(@PathVariable Long id) {
        ApiResponse<Booking> response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            // 1. Tìm HaircutService theo tên
            ApiResponse<List<HaircutService>> serviceResponse = serviceService.searchServicesByName(bookingRequest.getService());
            if (!serviceResponse.isSuccess() || serviceResponse.getData() == null || serviceResponse.getData().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Không tìm thấy dịch vụ: " + bookingRequest.getService()));
            }
            HaircutService haircutService = serviceResponse.getData().get(0);

            // 2. Tìm hoặc tạo Customer theo email hoặc số điện thoại
            Customer customer = null;
            ApiResponse<Customer> customerByEmailResponse = customerService.getCustomerByEmail(bookingRequest.getEmail());

            if (customerByEmailResponse.isSuccess() && customerByEmailResponse.getData() != null) {
                customer = customerByEmailResponse.getData();
            } else {
                ApiResponse<Customer> customerByPhoneResponse = customerService.getCustomerByPhone(bookingRequest.getPhone());
                if (customerByPhoneResponse.isSuccess() && customerByPhoneResponse.getData() != null) {
                    customer = customerByPhoneResponse.getData();
                } else {
                    // Tạo khách hàng mới
                    Customer newCustomer = new Customer();
                    newCustomer.setFullName(bookingRequest.getName());
                    newCustomer.setEmail(bookingRequest.getEmail());
                    newCustomer.setPhoneNumber(bookingRequest.getPhone());

                    ApiResponse<Customer> createdCustomerResponse = customerService.createCustomer(newCustomer);
                    if (createdCustomerResponse.isSuccess() && createdCustomerResponse.getData() != null) {
                        customer = createdCustomerResponse.getData();
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiResponse.error("Không thể tạo khách hàng mới: " + createdCustomerResponse.getMessage()));
                    }
                }
            }

            if (customer == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.error("Không thể tìm hoặc tạo khách hàng"));
            }

            // 3. Xử lý ngày giờ
            LocalDateTime bookingDateTime;
            try {
                // Parse chuỗi ngày giờ từ frontend, bỏ qua phần mili giây và múi giờ
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                bookingDateTime = LocalDateTime.parse(bookingRequest.getDatetime().substring(0, 19), formatter);
            } catch (DateTimeParseException | StringIndexOutOfBoundsException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Định dạng ngày giờ không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-ddTHH:mm:ssZ"));
            }

            LocalDate bookingDate = bookingDateTime.toLocalDate();
            LocalTime bookingTime = bookingDateTime.toLocalTime();

            // temporary log to check the time value
            System.out.println("DEBUG: Booking time before creating Booking object: " + bookingTime);

            // 4. Tạo đối tượng Booking
            Booking newBooking = new Booking();
            newBooking.setCustomer(customer);
            newBooking.setService(haircutService);
            newBooking.setBookingDate(bookingDate);
            newBooking.setBookingTime(bookingTime);
            newBooking.setStatus(BookingStatus.PENDING);
            newBooking.setNotes(bookingRequest.getNotes());

            // 5. Lưu Booking
            Booking savedBooking = bookingService.createBooking(newBooking).getData(); // Use bookingService

            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Đặt lịch thành công!", savedBooking));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Có lỗi xảy ra khi xử lý đặt lịch: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> updateBooking(@PathVariable Long id, @RequestBody BookingUpdateRequest bookingUpdateRequest) {
        ApiResponse<Booking> response = bookingService.updateBooking(id, bookingUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable Long id) {
        ApiResponse<Void> response = bookingService.deleteBooking(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByDate(@PathVariable String date) {
        ApiResponse<List<Booking>> response = bookingService.getBookingsByDate(date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByCustomer(@PathVariable Long customerId) {
        ApiResponse<List<Booking>> response = bookingService.getBookingsByCustomer(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByStaff(@PathVariable Long staffId) {
        ApiResponse<List<Booking>> response = bookingService.getBookingsByStaff(staffId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByStatus(@PathVariable Booking.BookingStatus status) {
        ApiResponse<List<Booking>> response = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(response);
    }

    // New endpoint to update booking status
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateBookingStatus(@PathVariable Long id, @RequestBody BookingStatusUpdateRequest request) {
        try {
            System.out.println("DEBUG: Received status string: " + request.getStatus());
            BookingStatus newStatus = BookingStatus.valueOf(request.getStatus().toUpperCase());
            ApiResponse<Booking> response = bookingService.updateBookingStatus(id, newStatus);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(ApiResponse.error("Trạng thái không hợp lệ: " + request.getStatus()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(ApiResponse.error("Lỗi khi cập nhật trạng thái đặt lịch: " + e.getMessage()));
        }
    }

    // New endpoint to get pending bookings
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Booking>>> getPendingBookings() {
        ApiResponse<List<Booking>> response = bookingService.getPendingBookings();
        return ResponseEntity.ok(response);
    }
} 