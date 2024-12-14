package controllers;

import models.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private final String USER = "USER";
    private final String PASSWORD = "PASSWORD";

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString("item_id"),
                        resultSet.getString("item_name"),
                        resultSet.getString("item_size"),
                        resultSet.getBigDecimal("item_price"),
                        resultSet.getString("item_category"),
                        resultSet.getString("item_status"),
                        resultSet.getString("item_wishlist"),
                        resultSet.getString("item_offer_status")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void approveItem(String itemId) {
        String query = "UPDATE items SET item_status = 'Approved' WHERE item_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, itemId);
            preparedStatement.executeUpdate();
            System.out.println("Item " + itemId + " approved.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void declineItem(String itemId) {
        String query = "UPDATE items SET item_status = 'Declined' WHERE item_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, itemId);
            preparedStatement.executeUpdate();
            System.out.println("Item " + itemId + " declined.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(String itemId, String itemName, String itemSize, BigDecimal itemPrice, String itemCategory) {
        String query = "INSERT INTO items (item_id, item_name, item_size, item_price, item_category, item_status, item_wishlist, item_offer_status) VALUES (?, ?, ?, ?, ?, 'Pending', 'No', 'Not Offered')";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, itemId);
            preparedStatement.setString(2, itemName);
            preparedStatement.setString(3, itemSize);
            preparedStatement.setBigDecimal(4, itemPrice);
            preparedStatement.setString(5, itemCategory);
            preparedStatement.executeUpdate();
            System.out.println("Item " + itemId + " added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String itemId) {
        String query = "DELETE FROM items WHERE item_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, itemId);
            preparedStatement.executeUpdate();
            System.out.println("Item " + itemId + " deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editItem(String itemId, String itemName, String itemCategory, String itemSize, BigDecimal itemPrice) {
        String query = "UPDATE items SET item_name = ?, item_category = ?, item_size = ?, item_price = ? WHERE item_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, itemName);
            preparedStatement.setString(2, itemCategory);
            preparedStatement.setString(3, itemSize);
            preparedStatement.setBigDecimal(4, itemPrice);
            preparedStatement.setString(5, itemId);
            preparedStatement.executeUpdate();
            System.out.println("Item " + itemId + " updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
