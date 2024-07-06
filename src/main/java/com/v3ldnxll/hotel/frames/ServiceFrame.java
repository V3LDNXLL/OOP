package com.v3ldnxll.hotel.frames;

import com.v3ldnxll.hotel.repositories.ServiceRepository;
import com.v3ldnxll.hotel.types.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ServiceFrame extends JFrame {
    private final ServiceRepository serviceRepository;
    private final DefaultListModel<Service> serviceListModel;

    public ServiceFrame(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceListModel = new DefaultListModel<>();

        setTitle("Послуги");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JList<Service> serviceList = new JList<>(serviceListModel);
        serviceList.setCellRenderer(new ServiceListRenderer());
        panel.add(new JScrollPane(serviceList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton addButton = new JButton("Додати");
        addButton.addActionListener(this::showAddServiceDialog);
        JButton removeButton = new JButton("Видалити");
        removeButton.addActionListener(event -> removeSelectedService(serviceList.getSelectedValue()));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        loadServices();
    }

    private void loadServices() {
        List<Service> services = serviceRepository.findAll();
        serviceListModel.clear(); // Очищуємо модель перед завантаженням нових даних
        services.forEach(serviceListModel::addElement); // Додаємо всі послуги з бази даних в модель
    }

    private void showAddServiceDialog(ActionEvent event) {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();

        Object[] fields = {
            "Назва:", nameField,
            "Опис:", descriptionField,
            "Ціна:", priceField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Додати послугу", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            double price = Double.parseDouble(priceField.getText());
            Service service = new Service(0, nameField.getText(), descriptionField.getText(), price);
            serviceRepository.save(service); // Зберігаємо нову послугу у базі даних
            serviceListModel.addElement(service); // Додаємо нову послугу до моделі списку
        }
    }

    private void removeSelectedService(Service service) {
        if (service != null) {
            serviceRepository.delete(service.id());
            serviceListModel.removeElement(service); // Видаляємо послугу з моделі списку
        }
    }
}

class ServiceListRenderer extends JLabel implements ListCellRenderer<Service> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Service> list, Service service, int index, boolean isSelected, boolean cellHasFocus) {
        setText(service.name() + " - " + service.description() + " (₴" + service.price() + ")");

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setOpaque(true);
        return this;
    }
}
