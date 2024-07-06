package com.v3ldnxll.hotel.repositories.jdbc;

import com.v3ldnxll.hotel.DatabaseConfig;
import com.v3ldnxll.hotel.repositories.BookingRepository;
import com.v3ldnxll.hotel.types.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBookingRepository implements BookingRepository {

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                bookings.add(new Booking(
                    resultSet.getInt("id"),
                    resultSet.getInt("clientId"),
                    resultSet.getInt("serviceId"),
                    resultSet.getString("startDate"),
                    resultSet.getString("endDate"),
                    resultSet.getDouble("totalPrice")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public Optional<Booking> findById(int id) {
        String query = "SELECT * FROM Bookings WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Booking(
                    resultSet.getInt("id"),
                    resultSet.getInt("clientId"),
                    resultSet.getInt("serviceId"),
                    resultSet.getString("startDate"),
                    resultSet.getString("endDate"),
                    resultSet.getDouble("totalPrice")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Booking booking) {
        String query = "INSERT INTO Bookings (clientId, serviceId, startDate, endDate, totalPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, booking.clientId());
            statement.setInt(2, booking.serviceId());
            statement.setString(3, booking.startDate());
            statement.setString(4, booking.endDate());
            statement.setDouble(5, booking.totalPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Booking booking) {
        String query = "UPDATE Bookings SET clientId = ?, serviceId = ?, startDate = ?, endDate = ?, totalPrice = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, booking.clientId());
            statement.setInt(2, booking.serviceId());
            statement.setString(3, booking.startDate());
            statement.setString(4, booking.endDate());
            statement.setDouble(5, booking.totalPrice());
            statement.setInt(6, booking.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Bookings WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
