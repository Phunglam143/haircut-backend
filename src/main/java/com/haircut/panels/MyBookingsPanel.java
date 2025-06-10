package com.haircut.panels;

import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import com.haircut.services.BookingService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

public class MyBookingsPanel extends JPanel {
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private JButton refreshButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    private JComboBox<String> statusFilter;
    private TableRowSorter<DefaultTableModel> sorter;

    @Autowired
    private BookingService bookingService;

    public MyBookingsPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    @PostConstruct
    public void init() {
        loadBookings();
    }

    private void initComponents() {
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Sẵn sàng");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo trạng thái:"));
        statusFilter = new JComboBox<>(new String[]{"Tất cả", "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"});
        statusFilter.addActionListener(e -> applyFilter());
        filterPanel.add(statusFilter);
        add(filterPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Khách hàng", "Dịch vụ", "Nhân viên", "Ngày", "Giờ", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        bookingsTable.setRowSorter(sorter);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        bookingsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        bookingsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Khách hàng
        bookingsTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Dịch vụ
        bookingsTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Nhân viên
        bookingsTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Ngày
        bookingsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Giờ
        bookingsTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Trạng thái

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        refreshButton = new JButton("Làm mới");
        refreshButton.addActionListener(e -> loadBookings());
        buttonsPanel.add(refreshButton);
        
        cancelButton = new JButton("Hủy lịch");
        cancelButton.addActionListener(e -> cancelBooking());
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void applyFilter() {
        String selectedStatus = (String) statusFilter.getSelectedItem();
        if ("Tất cả".equals(selectedStatus)) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(selectedStatus, 6)); // Column 6 is status
        }
    }

    private void setLoading(boolean isLoading) {
        refreshButton.setEnabled(!isLoading);
        cancelButton.setEnabled(!isLoading);
        statusFilter.setEnabled(!isLoading);
        statusLabel.setText(isLoading ? "Đang tải..." : "Sẵn sàng");
    }

    private void loadBookings() {
        setLoading(true);
        try {
            tableModel.setRowCount(0); // Clear existing rows
            ApiResponse<List<Booking>> response = bookingService.getAllBookings();
            
            if (response.isSuccess() && response.getData() != null) {
                for (Booking booking : response.getData()) {
                    tableModel.addRow(new Object[]{
                        booking.getId(),
                        booking.getCustomer().getFullName(),
                        booking.getService().getName(),
                        booking.getStaff().getFullName(),
                        booking.getBookingDate().format(dateFormatter),
                        booking.getBookingTime().format(timeFormatter),
                        booking.getStatus().name()
                    });
                }
                statusLabel.setText("Đã tải " + response.getData().size() + " lịch hẹn");
                applyFilter(); // Apply current filter after loading
            } else {
                String errorMessage = response.getMessage() != null ? 
                    response.getMessage() : "Không thể tải danh sách lịch hẹn";
                JOptionPane.showMessageDialog(this,
                    errorMessage,
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("Lỗi khi tải dữ liệu");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách lịch hẹn: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi khi tải dữ liệu");
        } finally {
            setLoading(false);
        }
    }

    private void cancelBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn lịch hẹn cần hủy!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert view index to model index
        int modelRow = bookingsTable.convertRowIndexToModel(selectedRow);

        // Check current booking status
        String currentStatus = tableModel.getValueAt(modelRow, 6).toString();
        if (BookingStatus.CANCELLED.name().equals(currentStatus)) {
            JOptionPane.showMessageDialog(this,
                "Lịch hẹn này đã bị hủy trước đó!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (BookingStatus.COMPLETED.name().equals(currentStatus)) {
            JOptionPane.showMessageDialog(this,
                "Không thể hủy lịch hẹn đã hoàn thành!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Long bookingId = Long.valueOf(tableModel.getValueAt(modelRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn hủy lịch hẹn này?",
                "Xác nhận hủy lịch",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                setLoading(true);
                ApiResponse<Booking> response = bookingService.updateBookingStatus(bookingId, BookingStatus.CANCELLED);
                if (response.isSuccess() && response.getData() != null) {
                    tableModel.setValueAt(BookingStatus.CANCELLED.name(), modelRow, 6);
                    JOptionPane.showMessageDialog(this,
                        "Đã hủy lịch hẹn thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                    statusLabel.setText("Đã hủy lịch hẹn thành công");
                } else {
                    String errorMessage = response.getMessage() != null ?
                        response.getMessage() : "Không thể hủy lịch hẹn";
                    JOptionPane.showMessageDialog(this,
                        errorMessage,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("Lỗi khi hủy lịch hẹn");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: ID lịch hẹn không hợp lệ!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi: ID không hợp lệ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Hủy lịch hẹn thất bại: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi khi hủy lịch hẹn");
        } finally {
            setLoading(false);
        }
    }
} 