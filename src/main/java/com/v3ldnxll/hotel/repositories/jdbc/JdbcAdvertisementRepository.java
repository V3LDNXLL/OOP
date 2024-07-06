package com.v3ldnxll.hotel.repositories.jdbc;

import com.v3ldnxll.hotel.DatabaseConfig;
import com.v3ldnxll.hotel.repositories.AdvertisementRepository;
import com.v3ldnxll.hotel.types.Advertisement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAdvertisementRepository implements AdvertisementRepository {

    @Override
    public List<Advertisement> findAll() {
        List<Advertisement> advertisements = new ArrayList<>();
        String query = "SELECT * FROM Advertisements";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                advertisements.add(new Advertisement(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("startDate"),
                        resultSet.getString("endDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertisements;
    }

    @Override
    public Optional<Advertisement> findById(int id) {
        String query = "SELECT * FROM Advertisements WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Advertisement(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("startDate"),
                        resultSet.getString("endDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Advertisement advertisement) {
        String query = "INSERT INTO Advertisements (title, content, startDate, endDate) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, advertisement.title());
            statement.setString(2, advertisement.content());
            statement.setString(3, advertisement.startDate());
            statement.setString(4, advertisement.endDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Advertisement advertisement) {
        String query = "UPDATE Advertisements SET title = ?, content = ?, startDate = ?, endDate = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, advertisement.title());
            statement.setString(2, advertisement.content());
            statement.setString(3, advertisement.startDate());
            statement.setString(4, advertisement.endDate());
            statement.setInt(5, advertisement.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Advertisements WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
