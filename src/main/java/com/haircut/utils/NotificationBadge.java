package com.haircut.utils;

import javax.swing.*;
import java.awt.*;

public class NotificationBadge extends JLabel {
    private int count = 0;

    public NotificationBadge() {
        setOpaque(true);
        setBackground(Color.RED);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 12));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(20, 20));
        updateBadge();
    }

    public void setCount(int count) {
        this.count = count;
        updateBadge();
    }

    public int getCount() {
        return count;
    }

    private void updateBadge() {
        if (count > 0) {
            setText(String.valueOf(count));
            setVisible(true);
        } else {
            setText("");
            setVisible(false);
        }
    }
} 