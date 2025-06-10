package com.haircut.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "adminpassword"; // Thay đổi mật khẩu này theo ý bạn
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Mật khẩu đã mã hóa cho '" + rawPassword + "': " + encodedPassword);
    }
} 