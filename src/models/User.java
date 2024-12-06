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
	
	public static void login(String username, String password) {
		
	}
	
	public static void register(String username, String password, String phone_number, 
			String address) {
		
	}
	
	public static void checkAccountValidation(String username, String password,
			String phone_number, String address) {
		
	}

	// GETTER & SETTER
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	

}
