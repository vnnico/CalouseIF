package main;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class TestMain  {
	

	public static void main(String[] args) {
	        // URL, username, dan password untuk koneksi ke database
	        String url = "jdbc:mysql://localhost:3306/calouseif";
	        String user = "root"; // Ganti dengan username database Anda
	        String password = ""; // Ganti dengan password database Anda


	        try {
	            // Muat driver JDBC MySQL
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Koneksi ke database
	            Connection connection = DriverManager.getConnection(url, user, password);
	            System.out.println("Koneksi berhasil!");

	            // Buat statement
	            Statement statement = connection.createStatement();

	            // Contoh query SELECT
	            String query = "SELECT * FROM items";
	            ResultSet resultSet = statement.executeQuery(query);

	            // Iterasi hasil query
	            while (resultSet.next()) {
	                String id = resultSet.getString("item_id");
	                String itemName = resultSet.getString("item_name");
	                String itemSize = resultSet.getString("item_size");
	                BigDecimal itemPrice = resultSet.getBigDecimal("item_price");
	                String itemCategory = resultSet.getString("item_category");

	                System.out.println("ID: " + id + ", Nama: " + itemName + ", Size: " + itemSize
	                        + ", Price: " + itemPrice + ", Category: " + itemCategory);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}