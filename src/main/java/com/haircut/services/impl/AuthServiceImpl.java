package com.haircut.services.impl;

import com.haircut.dto.LoginRequest;
import com.haircut.dto.RegisterRequest;
import com.haircut.models.User;
import com.haircut.repository.UserRepository;
import com.haircut.services.AuthService;
import com.haircut.utils.ApiResponse;
import com.haircut.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public ApiResponse<User> register(RegisterRequest request) {
        System.out.println("Register request received: " + request);

        // Kiểm tra username đã tồn tại
        if (userRepository.existsByUsername(request.getUsername())) {
             System.out.println("Username already exists: " + request.getUsername());
            return ApiResponse.error("Tên đăng nhập đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(request.getEmail())) {
             System.out.println("Email already exists: " + request.getEmail());
            return ApiResponse.error("Email đã tồn tại");
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber()); // Ensure phoneNumber is handled even if null
        user.setRole(User.UserRole.CUSTOMER); // Mặc định là CUSTOMER
        user.setActive(true);

        System.out.println("User object before saving: " + user);

        // Lưu user
        try {
            user = userRepository.save(user);
            System.out.println("User object after saving: " + user);
            return ApiResponse.success("Đăng ký thành công", user);
        } catch (Exception e) {
            System.err.println("Error saving user during registration: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed error
            return ApiResponse.error("Lỗi khi lưu user: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<User> login(LoginRequest request) {
        // Tìm user theo username
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        // Kiểm tra user tồn tại
        if (user == null) {
             System.out.println("User not found: " + request.getUsername());
            return ApiResponse.error("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        // Kiểm tra password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch for user: " + request.getUsername());
            return ApiResponse.error("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        // Kiểm tra user có bị khóa không
        if (!user.isActive()) {
            System.out.println("Account disabled: " + request.getUsername());
            return ApiResponse.error("Tài khoản đã bị khóa");
        }

        System.out.println("Login successful: " + request.getUsername());

        // Manually authenticate and generate token without AuthenticationManager.authenticate()
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateToken(user);

        return ApiResponse.success("Đăng nhập thành công", user, jwt);
    }

    @Override
    public ApiResponse<Void> logout() {
        // Xử lý logout ở đây nếu cần
        return ApiResponse.success("Đăng xuất thành công", null);
    }
} 