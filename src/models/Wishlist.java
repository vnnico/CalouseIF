package models;

public class Wishlist {
	
	private String wishlist_id;
	private String item_id;
	private String user_id;
	
	public Wishlist(String wishlist_id, String item_id, String user_id) {
		super();
		this.wishlist_id = wishlist_id;
		this.item_id = item_id;
		this.user_id = user_id;
	}
	
	public static void viewWishlist(String wishlist_id, String user_id) {
		
	}
	
	public static void addWishlist(String item_id, String user_id) {
		
	}
	
	public static void removeWishlist(String wishlist_id) {
		
	}

	public String getWishlist_id() {
		return wishlist_id;
	}

	public void setWishlist_id(String wishlist_id) {
		this.wishlist_id = wishlist_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	
	

}
