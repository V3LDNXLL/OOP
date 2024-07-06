package com.v3ldnxll.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String JDBC_URL = "jdbc:h2:./hotel_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Clients (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "firstName VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "phoneNumber VARCHAR(255), " +
                    "address VARCHAR(255))");

            statement.execute("CREATE TABLE IF NOT EXISTS Services (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "price DOUBLE)");

            statement.execute("CREATE TABLE IF NOT EXISTS Advertisements (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "title VARCHAR(255), " +
                    "content VARCHAR(255), " +
                    "startDate VARCHAR(255), " +
                    "endDate VARCHAR(255))");

            statement.execute("CREATE TABLE IF NOT EXISTS Bookings (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "clientId INT, " +
                    "serviceId INT, " +
                    "startDate VARCHAR(255), " +
                    "endDate VARCHAR(255), " +
                    "totalPrice DOUBLE, " +
                    "FOREIGN KEY (clientId) REFERENCES Clients(id), " +
                    "FOREIGN KEY (serviceId) REFERENCES Services(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
