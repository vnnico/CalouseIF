package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConnectionSingleton {

	private static ConnectionSingleton db_connection;

	 // Database connection configuration details
	private final String HOST = "localhost:3306";
	private final String DATABASE = "calouseif";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	// Instance variables for managing database connection and query results
	private Connection connect;
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	
	private ConnectionSingleton() {
		try {

			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			// Establish the database connection 
			connect = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public ResultSet execQuery(PreparedStatement ps) {
		try {
			// Execute the query and store the result
			resultSet = ps.executeQuery();
			// Retrieve and store metadata about the query results
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public Integer execUpdate(PreparedStatement prepareStatement) {
		try {
			// Execute the update query and return the number of affected rows
			return prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		}		
	}
	
	public PreparedStatement prepareStatement(String query) {
		try {
			// Create and return the PreparedStatement for the provided query
			return connect.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static synchronized ConnectionSingleton getInstance() {
		// Create the instance if it does not already exist
		if(db_connection == null) db_connection = new ConnectionSingleton();
		
		return db_connection;
	}
	
}
