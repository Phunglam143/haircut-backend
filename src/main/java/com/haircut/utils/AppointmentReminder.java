package com.haircut.utils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentReminder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void showReminder(Component parent, String customerName, LocalDateTime appointmentTime) {
        String message = String.format(
            "Nhắc lịch: Khách hàng %s có lịch hẹn vào %s",
            customerName,
            appointmentTime.format(formatter)
        );
        Notification.showInfo(parent, message);
    }

    public static void showUpcomingAppointments(Component parent, int count) {
        String message = String.format(
            "Bạn có %d lịch hẹn sắp tới trong ngày hôm nay",
            count
        );
        Notification.showInfo(parent, message);
    }
} 