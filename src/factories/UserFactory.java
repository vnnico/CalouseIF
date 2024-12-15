package factories;

import models.User;

public class UserFactory {

	
	public UserFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static User createUser() {
		return new User();
	}
	
	public static User createUser(String user_id, String username, String password, String phone_number, String address, String role) {
		return new User(user_id, username, password, phone_number, address, role);
	}
}
