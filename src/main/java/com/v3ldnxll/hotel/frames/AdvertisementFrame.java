package com.v3ldnxll.hotel.frames;

import com.v3ldnxll.hotel.repositories.AdvertisementRepository;
import com.v3ldnxll.hotel.types.Advertisement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdvertisementFrame extends JFrame {
    private final AdvertisementRepository advertisementRepository;
    private final DefaultListModel<Advertisement> advertisementListModel;

    public AdvertisementFrame(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementListModel = new DefaultListModel<>();

        setTitle("Реклама");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JList<Advertisement> advertisementList = new JList<>(advertisementListModel);
        advertisementList.setCellRenderer(new AdvertisementListRenderer());
        panel.add(new JScrollPane(advertisementList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton addButton = new JButton("Додати");
        addButton.addActionListener(this::showAddAdvertisementDialog);
        JButton removeButton = new JButton("Видалити");
        removeButton.addActionListener(event -> removeSelectedAdvertisement(advertisementList.getSelectedValue()));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        loadAdvertisements();
    }

    private void loadAdvertisements() {
        advertisementListModel.clear();
        List<Advertisement> advertisements = advertisementRepository.findAll();
        advertisements.forEach(advertisementListModel::addElement);
    }

    private void showAddAdvertisementDialog(ActionEvent event) {
        JTextField titleField = new JTextField();
        JTextField contentField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        Object[] fields = {
            "Заголовок:", titleField,
            "Зміст:", contentField,
            "Дата початку:", startDateField,
            "Дата закінчення:", endDateField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Додати рекламу", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Advertisement advertisement = new Advertisement(0, titleField.getText(), contentField.getText(), startDateField.getText(), endDateField.getText());
            advertisementRepository.save(advertisement);
            loadAdvertisements();
        }
    }

    private void removeSelectedAdvertisement(Advertisement advertisement) {
        if (advertisement != null) {
            advertisementRepository.delete(advertisement.id());
            loadAdvertisements();
        }
    }
}

class AdvertisementListRenderer extends JLabel implements ListCellRenderer<Advertisement> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Advertisement> list, Advertisement advertisement, int index, boolean isSelected, boolean cellHasFocus) {
        setText(advertisement.title() + " - " + advertisement.content() + " (з " + advertisement.startDate() + " до " + advertisement.endDate() + ")");

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
