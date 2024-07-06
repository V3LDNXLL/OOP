package com.v3ldnxll.hotel.frames;

import com.v3ldnxll.hotel.repositories.BookingRepository;
import com.v3ldnxll.hotel.repositories.ClientRepository;
import com.v3ldnxll.hotel.repositories.ServiceRepository;
import com.v3ldnxll.hotel.types.Booking;
import com.v3ldnxll.hotel.types.Client;
import com.v3ldnxll.hotel.types.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BookingFrame extends JFrame {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final DefaultListModel<Booking> bookingListModel;

    public BookingFrame(BookingRepository bookingRepository, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.bookingListModel = new DefaultListModel<>();

        setTitle("Бронювання");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JList<Booking> bookingList = new JList<>(bookingListModel);
        bookingList.setCellRenderer(new BookingListRenderer(clientRepository, serviceRepository));
        panel.add(new JScrollPane(bookingList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton addButton = new JButton("Додати");
        addButton.addActionListener(this::showAddBookingDialog);
        JButton removeButton = new JButton("Видалити");
        removeButton.addActionListener(event -> removeSelectedBooking(bookingList.getSelectedValue()));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        loadBookings();
    }

    private void loadBookings() {
        bookingListModel.clear();
        List<Booking> bookings = bookingRepository.findAll();
        bookings.forEach(bookingListModel::addElement);
    }

    private void showAddBookingDialog(ActionEvent event) {
        List<Client> clients = clientRepository.findAll();
        List<Service> services = serviceRepository.findAll();

        JComboBox<Client> clientComboBox = new JComboBox<>(clients.toArray(new Client[0]));
        clientComboBox.setRenderer(new ClientComboBoxRenderer());

        JComboBox<Service> serviceComboBox = new JComboBox<>(services.toArray(new Service[0]));
        serviceComboBox.setRenderer(new ServiceComboBoxRenderer());

        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JTextField totalPriceField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Клієнт:"));
        panel.add(clientComboBox);
        panel.add(new JLabel("Послуга:"));
        panel.add(serviceComboBox);
        panel.add(new JLabel("Дата початку:"));
        panel.add(startDateField);
        panel.add(new JLabel("Дата закінчення:"));
        panel.add(endDateField);
        panel.add(new JLabel("Загальна ціна:"));
        panel.add(totalPriceField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Додати бронювання", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Client client = (Client) clientComboBox.getSelectedItem();
            Service service = (Service) serviceComboBox.getSelectedItem();
            double totalPrice = Double.parseDouble(totalPriceField.getText());
            Booking booking = new Booking(0, client.id(), service.id(), startDateField.getText(), endDateField.getText(), totalPrice);
            bookingRepository.save(booking);
            loadBookings();
        }
    }

    private void removeSelectedBooking(Booking booking) {
        if (booking != null) {
            bookingRepository.delete(booking.id());
            loadBookings();
        }
    }
}

class BookingListRenderer extends JLabel implements ListCellRenderer<Booking> {
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    public BookingListRenderer(ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Booking> list, Booking booking, int index, boolean isSelected, boolean cellHasFocus) {
        Client client = clientRepository.findById(booking.clientId()).orElse(null);
        Service service = serviceRepository.findById(booking.serviceId()).orElse(null);

        String clientInfo = (client != null) ? client.firstName() + " " + client.lastName() : "Невідомий клієнт";
        String serviceInfo = (service != null) ? service.name() : "Невідома послуга";

        setText(clientInfo + " - " + serviceInfo + " (з " + booking.startDate() + " до " + booking.endDate() + ") - ₴" + booking.totalPrice());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}

class ClientComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Client) {
            Client client = (Client) value;
            setText(client.firstName() + " " + client.lastName());
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}

class ServiceComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Service) {
            Service service = (Service) value;
            setText(service.name() + " (₴" + service.price() + ")");
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
