package com.haircut.panels;

import com.haircut.models.*;
import com.haircut.services.*;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingPanel extends JPanel {
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private StaffService staffService;

    private JComboBox<Customer> customerComboBox;
    private JComboBox<HaircutService> serviceComboBox;
    private JComboBox<Staff> staffComboBox;
    private JTextField dateField;
    private JTextField timeField;
    private JTextArea notesArea;
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public BookingPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    @PostConstruct
    public void init() {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi khởi tạo dữ liệu: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshData() {
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeComponents() {
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Customer:"), gbc);
        customerComboBox = new JComboBox<>();
        gbc.gridx = 1;
        formPanel.add(customerComboBox, gbc);

        // Service
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Service:"), gbc);
        serviceComboBox = new JComboBox<>();
        gbc.gridx = 1;
        formPanel.add(serviceComboBox, gbc);

        // Staff
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Staff:"), gbc);
        staffComboBox = new JComboBox<>();
        gbc.gridx = 1;
        formPanel.add(staffComboBox, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Date:"), gbc);
        dateField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        // Time
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Time:"), gbc);
        timeField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(timeField, gbc);

        // Notes
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Notes:"), gbc);
        notesArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(notesArea), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> createBooking());
        buttonPanel.add(bookButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columns = {"ID", "Customer", "Service", "Staff", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);

        // Add components to main panel
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        // Load customers
        ApiResponse<List<Customer>> customerResponse = customerService.getAllCustomers();
        if (customerResponse.isSuccess() && customerResponse.getData() != null) {
            customerComboBox.removeAllItems();
            for (Customer customer : customerResponse.getData()) {
                customerComboBox.addItem(customer);
            }
        }

        // Load services
        ApiResponse<List<HaircutService>> serviceResponse = serviceService.getAllServices();
        if (serviceResponse.isSuccess() && serviceResponse.getData() != null) {
            serviceComboBox.removeAllItems();
            for (HaircutService service : serviceResponse.getData()) {
                serviceComboBox.addItem(service);
            }
        }

        // Load staff
        ApiResponse<List<Staff>> staffResponse = staffService.getActiveStaff();
        if (staffResponse.isSuccess() && staffResponse.getData() != null) {
            staffComboBox.removeAllItems();
            for (Staff staff : staffResponse.getData()) {
                staffComboBox.addItem(staff);
            }
        }

        // Load bookings
        ApiResponse<List<Booking>> bookingResponse = bookingService.getAllBookings();
        if (bookingResponse.isSuccess() && bookingResponse.getData() != null) {
            tableModel.setRowCount(0);
            for (Booking booking : bookingResponse.getData()) {
                Object[] row = {
                    booking.getId(),
                    booking.getCustomer().getFullName(),
                    booking.getService().getName(),
                    booking.getStaff().getFullName(),
                    booking.getBookingDate(),
                    booking.getBookingTime(),
                    booking.getStatus()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void createBooking() {
        try {
            Customer customer = (Customer) customerComboBox.getSelectedItem();
            HaircutService service = (HaircutService) serviceComboBox.getSelectedItem();
            Staff staff = (Staff) staffComboBox.getSelectedItem();
            LocalDate date = LocalDate.parse(dateField.getText());
            LocalTime time = LocalTime.parse(timeField.getText());

            Booking booking = new Booking(customer, service, staff, date, time);
            booking.setNotes(notesArea.getText());
            booking.setStatus(Booking.BookingStatus.PENDING);

            ApiResponse<Booking> response = bookingService.createBooking(booking);
            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this, "Booking created successfully!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Error: " + response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        customerComboBox.setSelectedIndex(0);
        serviceComboBox.setSelectedIndex(0);
        staffComboBox.setSelectedIndex(0);
        dateField.setText("");
        timeField.setText("");
        notesArea.setText("");
    }
} 