package com.v3ldnxll.hotel.frames;

import com.v3ldnxll.hotel.repositories.ClientRepository;
import com.v3ldnxll.hotel.types.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClientFrame extends JFrame {
    private final ClientRepository clientRepository;
    private final DefaultListModel<Client> clientListModel;

    public ClientFrame(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.clientListModel = new DefaultListModel<>();

        setTitle("Клієнти");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JList<Client> clientList = new JList<>(clientListModel);
        clientList.setCellRenderer(new ClientListRenderer());
        panel.add(new JScrollPane(clientList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton addButton = new JButton("Додати");
        addButton.addActionListener(this::showAddClientDialog);
        JButton removeButton = new JButton("Видалити");
        removeButton.addActionListener(event -> removeSelectedClient(clientList.getSelectedValue()));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        loadClients();
    }

    private void loadClients() {
        clientListModel.clear();
        List<Client> clients = clientRepository.findAll();
        clients.forEach(clientListModel::addElement);
    }

    private void showAddClientDialog(ActionEvent event) {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField addressField = new JTextField();

        Object[] fields = {
            "Ім'я:", firstNameField,
            "Прізвище:", lastNameField,
            "Електронна пошта:", emailField,
            "Номер телефону:", phoneNumberField,
            "Адреса:", addressField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Додати клієнта", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Client client = new Client(0, firstNameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), addressField.getText());
            clientRepository.save(client);
            loadClients();
        }
    }

    private void removeSelectedClient(Client client) {
        if (client != null) {
            clientRepository.delete(client.id());
            loadClients();
        }
    }
}

class ClientListRenderer extends JLabel implements ListCellRenderer<Client> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Client> list, Client client, int index, boolean isSelected, boolean cellHasFocus) {
        setText(client.firstName() + " " + client.lastName() + " - " + client.email());

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
