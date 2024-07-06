package com.v3ldnxll.hotel.frames;

import com.v3ldnxll.hotel.repositories.AdvertisementRepository;
import com.v3ldnxll.hotel.repositories.BookingRepository;
import com.v3ldnxll.hotel.repositories.ClientRepository;
import com.v3ldnxll.hotel.repositories.ServiceRepository;
import com.v3ldnxll.hotel.repositories.jdbc.JdbcAdvertisementRepository;
import com.v3ldnxll.hotel.repositories.jdbc.JdbcBookingRepository;
import com.v3ldnxll.hotel.repositories.jdbc.JdbcClientRepository;
import com.v3ldnxll.hotel.repositories.jdbc.JdbcServiceRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AdvertisementRepository advertisementRepository;
    private final BookingRepository bookingRepository;

    public MainFrame() {
        this.clientRepository = new JdbcClientRepository();
        this.serviceRepository = new JdbcServiceRepository();
        this.advertisementRepository = new JdbcAdvertisementRepository();
        this.bookingRepository = new JdbcBookingRepository();

        setTitle("Готель 1");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Додання текстового блоку з інформацією
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>Це адміністративна панель для керування готельними послугами.</div></html>");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(infoLabel, BorderLayout.NORTH);

        // Панель з кнопками
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 50, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));

        ImageIcon clientIcon = new ImageIcon(getClass().getResource("/icons/clients.png"));
        Image clientImage = clientIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledClientIcon = new ImageIcon(clientImage);
        JButton clientButton = new JButton("<html><center>Клієнти<br>у системі</center></html>", scaledClientIcon);
        clientButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        clientButton.setHorizontalTextPosition(SwingConstants.CENTER);
        clientButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clientButton.setPreferredSize(new Dimension(150, 150)); // Встановлення точного розміру кнопки
        clientButton.addActionListener(this::showClientScreen);

        ImageIcon serviceIcon = new ImageIcon(getClass().getResource("/icons/service.png"));
        Image serviceImage = serviceIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledServiceIcon = new ImageIcon(serviceImage);
        JButton serviceButton = new JButton("<html><center>Список<br>послуг</center></html>", scaledServiceIcon);
        serviceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        serviceButton.setHorizontalTextPosition(SwingConstants.CENTER);
        serviceButton.setFont(new Font("Arial", Font.PLAIN, 14));
        serviceButton.setPreferredSize(new Dimension(150, 150)); // Встановлення точного розміру кнопки
        serviceButton.addActionListener(this::showServiceScreen);

        ImageIcon advertisementIcon = new ImageIcon(getClass().getResource("/icons/advertisement.png"));
        Image advertisementImage = advertisementIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledAdvertisementIcon = new ImageIcon(advertisementImage);
        JButton advertisementButton = new JButton("<html><center>Рекламні<br>акції</center></html>", scaledAdvertisementIcon);
        advertisementButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        advertisementButton.setHorizontalTextPosition(SwingConstants.CENTER);
        advertisementButton.setFont(new Font("Arial", Font.PLAIN, 14));
        advertisementButton.setPreferredSize(new Dimension(150, 150)); // Встановлення точного розміру кнопки
        advertisementButton.addActionListener(this::showAdvertisementScreen);

        ImageIcon bookingIcon = new ImageIcon(getClass().getResource("/icons/booking.png"));
        Image bookingImage = bookingIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledBookingIcon = new ImageIcon(bookingImage);
        JButton bookingButton = new JButton("<html><center>Бронювання<br>кімнат</center></html>", scaledBookingIcon);
        bookingButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        bookingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        bookingButton.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingButton.setPreferredSize(new Dimension(150, 150)); // Встановлення точного розміру кнопки
        bookingButton.addActionListener(this::showBookingScreen);

        buttonPanel.add(clientButton);
        buttonPanel.add(serviceButton);
        buttonPanel.add(advertisementButton);
        buttonPanel.add(bookingButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBackground(new Color(240, 240, 240)); // Задаємо фоновий колір

        add(panel);
    }

    private void showClientScreen(ActionEvent event) {
        JFrame clientFrame = new ClientFrame(clientRepository);
        clientFrame.setVisible(true);
    }

    private void showServiceScreen(ActionEvent event) {
        JFrame serviceFrame = new ServiceFrame(serviceRepository);
        serviceFrame.setVisible(true);
    }

    private void showAdvertisementScreen(ActionEvent event) {
        JFrame advertisementFrame = new AdvertisementFrame(advertisementRepository);
        advertisementFrame.setVisible(true);
    }

    private void showBookingScreen(ActionEvent event) {
        JFrame bookingFrame = new BookingFrame(bookingRepository, clientRepository, serviceRepository);
        bookingFrame.setVisible(true);
    }
}
