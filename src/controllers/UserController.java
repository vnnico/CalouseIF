package controllers;

import models.User;
import services.Response;
import utils.ValidatePassword;
import utils.ValidatePhoneNumber;

public class UserController {

	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * LOGIN
	 * [GUEST]
	 * @param Username
	 * @param Password
	 * @return
	 */
	public static Response<User> Login(String Username, String Password) {
		Response<User> res = new Response<User>();
		
		if(Username.isEmpty()) {
			res.setMessages("Username cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.isEmpty()) {
			res.setMessages("Password cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return User.Login(Username, Password);
	}
	
	/**
	 * REGISTER
	 * [GUEST]
	 * @param Username
	 * @param Password
	 * @param Phone_Number
	 * @param Address
	 * @param Role
	 * @return
	 */
	public static Response<User> Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		Response<User> res = new Response<User>();
		
		if(Username.isEmpty()) {
			res.setMessages("Username cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.isEmpty()) {
			res.setMessages("Password cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Phone_Number.isEmpty()) {
			res.setMessages("Phone Number cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Address.isEmpty()) {
			res.setMessages("Address cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Role.isEmpty()) {
			res.setMessages("Role cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Username.length() < 3) {
			res.setMessages("Username must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Password.length() < 8) {
			res.setMessages("Password must at least be 8 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Phone_Number.length() < 12) {
			res.setMessages("Phone Number must at least be 10 numbers long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(User.CheckAccountValidation(Username, Password, Phone_Number, Address).getIsSuccess()) {
			res.setMessages("Username must be unique!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(!ValidatePassword.validate(Password)) {
			res.setMessages("Password must include special characters (!, @, #, $, %, ^, &, *)!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(!ValidatePhoneNumber.validate(Phone_Number)) {
			res.setMessages("Phone Number must at least contains a +62!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return User.Register(Username, Password, Phone_Number, Address, Role);
	}
	
	public static Response<User> CheckAccountValidation(String Username, String Password, String Phone_Number, String Address) {
		
		return null;
	}
	

}
