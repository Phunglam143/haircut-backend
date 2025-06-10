package com.haircut;

import com.haircut.panels.BookingPanel;
import com.haircut.panels.MyBookingsPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

/*
@Component
public class SwingInitializer implements CommandLineRunner {
    private final BookingPanel bookingPanel;
    private final MyBookingsPanel myBookingsPanel;

    @Autowired
    public SwingInitializer(BookingPanel bookingPanel, MyBookingsPanel myBookingsPanel) {
        this.bookingPanel = bookingPanel;
        this.myBookingsPanel = myBookingsPanel;
    }

    @Override
    public void run(String... args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hệ thống đặt lịch cắt tóc");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Đặt lịch", bookingPanel);
            tabbedPane.addTab("Lịch hẹn của tôi", myBookingsPanel);

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}
*/ 