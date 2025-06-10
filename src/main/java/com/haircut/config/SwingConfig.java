package com.haircut.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.swing.*;
import java.awt.*;

@Configuration
public class SwingConfig {
    
    /*
    @Bean
    public JFrame mainFrame() {
        JFrame frame = new JFrame("Haircut Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        return frame;
    }
    */

    @Bean
    public UIManager.LookAndFeelInfo lookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
} 