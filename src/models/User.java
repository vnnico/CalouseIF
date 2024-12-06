package models;

public class User {
	
	private String user_id;
	private String username;
	private String password;
	private String phone_number;
	private String address;
	private String role;
	
	public User(String user_id, String username, String password, String phone_number, String address, String role) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.phone_number = phone_number;
		this.address = address;
		this.role = role;
	}
	
	

}
