package models;

import java.util.*;

import factories.WishlistFactory;
import services.Response;
import utils.GenerateID;

public class Wishlist extends Model {
	
	private String wishlist_id;
	private String product_id;
	private String user_id;
	
	private final String tableName = "wishlists";
	private final String primaryKey = "wishlist_id";
	
	public Wishlist() {
		// TODO Auto-generated constructor stub
	}
	
	public Wishlist(String wishlist_id, String product_id, String user_id) {
		super();
		this.wishlist_id = wishlist_id;
		this.product_id = product_id;
		this.user_id = user_id;
	}
	
	/**
	 * VIEW ALL WISHLIST
	 * [BUYER]
	 * @param User_id
	 * @return
	 */
	public static Response<ArrayList<Wishlist>> ViewWishlist(String User_id) {
	    Response<ArrayList<Wishlist>> res = new Response<>();

	    try {
	        ArrayList<Wishlist> listWishlist = WishlistFactory.createWishlist().where("User_id", "=", User_id);
	        
	        res.setMessages("Success: Fetched all wishlist");
	        res.setIsSuccess(true);
	        res.setData(listWishlist);
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
	 * ADD AN ITEM TO WISHLIST
	 * [BUYER]
	 * @param Product_id
	 * @param User_id
	 * @return
	 */
	public static Response<Wishlist> AddWishlist(String Product_id, String User_id) {
	    Response<Wishlist> res = new Response<>();

	    try {
	    	
	    	// Create new wishlist and insert into database.
	        Wishlist wishlist = WishlistFactory.createWishlist(
	            GenerateID.generateNewId(WishlistFactory.createWishlist().latest().getWishlist_id(), "WS"), 
	            Product_id, User_id
	        );

	        wishlist.insert();
	        
	        res.setMessages("Success: Wishlist created");
	        res.setIsSuccess(true);
	        res.setData(wishlist);
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
	 * REMOVE WISHLIST
	 * [BUYER]
	 * @param Wishlist_id
	 * @return
	 */
	public static Response<Wishlist> RemoveWishlist(String Wishlist_id) {
	    Response<Wishlist> res = new Response<>();

	    try {
	        Boolean wishlist = WishlistFactory.createWishlist().delete(Wishlist_id);
	        
	        if (!wishlist) {
	            res.setMessages("Error: Deleting Wishlist Failed!");
	            res.setIsSuccess(wishlist);
	            res.setData(null);
	            return res;                
	        }
	        
	        res.setMessages("Success: Wishlist Removed!");
	        res.setIsSuccess(wishlist);
	        res.setData(null);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}


	public String getWishlist_id() {
		return wishlist_id;
	}

	public void setWishlist_id(String wishlist_id) {
		this.wishlist_id = wishlist_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	protected String getTablename() {
		// TODO Auto-generated method stub
		return tableName;
	}

	@Override
	protected String getPrimarykey() {
		// TODO Auto-generated method stub
		return primaryKey;
	}
	
	public User user() {
	    return this.hasOne(User.class, "users", this.user_id, "User_id");
	}

	public Product product() {
	    return this.hasOne(Product.class, "products", this.product_id, "Product_id");
	}

	public ArrayList<Wishlist> all() {
	    return super.all(Wishlist.class);
	}

	public ArrayList<Wishlist> where(String columnName, String operator, String key) {
	    return super.where(Wishlist.class, columnName, operator, key);
	}

	public Wishlist update(String fromKey) {
	    return super.update(Wishlist.class, fromKey);
	}

	public Wishlist insert() {
	    return super.insert(Wishlist.class);
	}

	public Wishlist find(String fromKey) {
	    return super.find(Wishlist.class, fromKey);
	}

	public Wishlist latest() {
	    return super.latest(Wishlist.class);
	}

	public Boolean delete(String fromKey) {
	    return super.delete(Wishlist.class, fromKey);
	}

	public ArrayList<Wishlist> whereIn(String columnName, ArrayList<String> listValues) {
	    return super.whereIn(Wishlist.class, columnName, listValues);
	}

	

}
