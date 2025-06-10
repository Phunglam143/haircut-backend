package com.haircut.config;

import com.haircut.models.User;
import com.haircut.models.HaircutService;
import com.haircut.repository.UserRepository;
import com.haircut.repository.HaircutServiceRepository;
import com.haircut.repository.BookingRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HaircutServiceRepository haircutServiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Mật khẩu là admin123
            adminUser.setEmail("admin@haircut.com");
            adminUser.setFullName("System Administrator");
            adminUser.setPhoneNumber("0123456789");
            adminUser.setRole(User.UserRole.ADMIN);
            adminUser.setActive(true);
            userRepository.save(adminUser);
            System.out.println("Admin user created successfully!");
        }

        // Clear existing bookings to prevent foreign key constraint issues
        bookingRepository.deleteAll();

        // Initialize Haircut Services
        // Clear existing services to ensure fresh data during development
        haircutServiceRepository.deleteAll();

        if (haircutServiceRepository.count() == 0) {
            haircutServiceRepository.save(new HaircutService("Cắt tóc", "Dịch vụ cắt tóc chuyên nghiệp với nhiều kiểu tóc hiện đại", 150000.0, 60, "/images/haircut.jpg"));
            haircutServiceRepository.save(new HaircutService("Nhuộm tóc", "Nhuộm tóc với các màu sắc đa dạng và chất lượng cao", 500000.0, 120, "/images/coloring.jpg"));
            haircutServiceRepository.save(new HaircutService("Uốn tóc", "Uốn tóc với công nghệ hiện đại, an toàn cho tóc", 800000.0, 180, "/images/perm.jpg"));
            haircutServiceRepository.save(new HaircutService("Gội đầu", "Gội đầu với dầu gội cao cấp và massage da đầu", 100000.0, 30, "/images/shampoo.jpg"));
            haircutServiceRepository.save(new HaircutService("Tạo kiểu", "Tạo kiểu tóc theo yêu cầu cho các dịp đặc biệt", 200000.0, 45, "/images/styling.jpg"));
            haircutServiceRepository.save(new HaircutService("Phục hồi tóc", "Phục hồi tóc hư tổn với các sản phẩm chuyên dụng", 300000.0, 90, "/images/hair-treatment.jpg"));
            System.out.println("Sample Haircut Services created successfully!");
        }
    }
} 