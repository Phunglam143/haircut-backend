package com.haircut;

import com.haircut.panels.BookingPanel;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Khởi động Spring Boot từ HaircutApplication
        ConfigurableApplicationContext context = HaircutApplication.mainContext();

        // Tự động mở trình duyệt đến frontend/backend
        try {
            Thread.sleep(3000); // Đợi backend khởi động
            String url = "http://localhost:8080"; // Đổi lại nếu frontend chạy port khác
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Nếu vẫn muốn mở GUI Swing thì giữ lại đoạn dưới
        // BookingPanel bookingPanel = context.getBean(BookingPanel.class);
        // SwingUtilities.invokeLater(() -> {
        //     JFrame frame = new JFrame("Haircut Booking System");
        //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //     frame.getContentPane().add(bookingPanel);
        //     frame.pack();
        //     frame.setLocationRelativeTo(null);
        //     frame.setVisible(true);
        // });
    }
}
