package models;

import java.util.*;

import factories.UserFactory;
import services.Response;
import utils.GenerateID;

public class User extends Model{
	
	private String user_id;
	private String username;
	private String password;
	private String phone_number;
	private String address;
	private String role;
	
	private final String tableName = "users";
	private final String primaryKey = "user_id";
	
	public User () {
		
	}
	
	public User(String user_id, String username, String password, String phone_number, String address, String role) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.phone_number = phone_number;
		this.address = address;
		this.role = role;
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
		    
		    try {
		    	
		    	
		    	ArrayList<User> user = UserFactory.createUser().where("Username", "=", Username);
		        if(user.isEmpty()) {
		        	res.setMessages("Error: User not found");
		            res.setIsSuccess(false);
		            res.setData(null);
		            return res;
		        }
		    	
		    	User isFound = user.get(0);
		    	// Authenticate user's password
		        if (!isFound.getPassword().equals(Password)) {
		            res.setMessages("Error: Wrong password");
		            res.setIsSuccess(false);
		            res.setData(null);
		            return res;
		        }
		        
		        res.setMessages("Success: User Authenticated");
		        res.setIsSuccess(true);
		        res.setData(isFound);
		        return res;
		        
		    }  catch (Exception e) {
		        e.printStackTrace();
		        res.setMessages("Error: " + e.getMessage() + "!");
		        res.setIsSuccess(false);
		        res.setData(null);
		        return res;
		    }
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
	    Response<User> res = new Response<>();
	    
	    try {
	    	
	    	// CREATE USER and insert into database
	        User user = UserFactory.createUser(
	            GenerateID.generateNewId(UserFactory.createUser().latest().getUser_id(), "US"),
	            Username, Password, Phone_Number, Address, Role
	        );
	        user.insert();
	        
	        res.setMessages("Success: User Registered!");
	        res.setIsSuccess(true);
	        res.setData(user);
	        return res;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}

	/**
	 * VALIDATE USER
	 * [GUEST]
	 * @param Username
	 * @param Password
	 * @param Phone_Number
	 * @param Address
	 * @return
	 */
	public static Response<User> CheckAccountValidation(String Username, String Password, String Phone_Number, String Address) {
		Response<User> res = new Response<User>();
	    try {
	    	
	    	
	    	ArrayList<User> user = UserFactory.createUser().where("Username", "=", Username);
	    	if(user.isEmpty()) {
	    		res.setMessages("Error: User not found");
	    		res.setIsSuccess(false);
	    		res.setData(null);
	    		return res;
	    	}
	    	
	    	res.setMessages("Success: User found");
	    	res.setIsSuccess(true);
	    	res.setData(user.get(0));
	    	return res;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
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

	@Override
	protected String getTablename() {
		// TODO Auto-generated method stub
		return this.tableName;
	}

	@Override
	protected String getPrimarykey() {
		// TODO Auto-generated method stub
		return this.primaryKey;
	}
	


	public ArrayList<Product> products() {
	    return this.hasMany(Product.class, "products", this.user_id, "Seller_id");
	}

	public ArrayList<Wishlist> wishlists() {
	    return this.hasMany(Wishlist.class, "wishlists", this.user_id, "User_id");
	}

	public ArrayList<Transaction> transactions() {
	    return this.hasMany(Transaction.class, "transactions", this.user_id, "User_id");
	}

	public ArrayList<Offer> offers() {
	    return this.hasMany(Offer.class, "offers", this.user_id, "Buyer_id");
	}

	public ArrayList<User> all() {
	    return super.all(User.class);
	}

	public ArrayList<User> where(String columnName, String operator, String key) {
	    return super.where(User.class, columnName, operator, key);
	}

	public User update(String fromKey) {
	    return super.update(User.class, fromKey);
	}

	public User insert() {
	    return super.insert(User.class);
	}

	public User find(String fromKey) {
	    return super.find(User.class, fromKey);
	}

	public User latest() {
	    return super.latest(User.class);
	}

	public Boolean delete(String fromKey) {
	    return super.delete(User.class, fromKey);
	}

	public ArrayList<User> whereIn(String columnName, ArrayList<String> listValues) {
	    return super.whereIn(User.class, columnName, listValues);
	}

	
	
	

}
