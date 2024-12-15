package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConnectionSingleton {

	private static ConnectionSingleton db_connection;

	private final String HOST = "localhost:3306";
	private final String DATABASE = "calouself";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection connect;
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	
	private ConnectionSingleton() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // versi 2 (PC Binus)
			connect = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public ResultSet execQuery(PreparedStatement ps) {
		try {
			resultSet = ps.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public Integer execUpdate(PreparedStatement prepareStatement) {
		try {
			
			return prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		}		
	}
	
	public PreparedStatement prepareStatement(String query) {
		try {
			return connect.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static synchronized ConnectionSingleton getInstance() {
		if(db_connection == null) db_connection = new ConnectionSingleton();
		
		return db_connection;
	}
	
}
