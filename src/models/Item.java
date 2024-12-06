package models;

public class Item {

	private String item_id;
	private String item_name;
	private String item_size;
	private String item_price;
	private String item_category;
	private String item_status;
	private String item_wishlist;
	private String item_offer_status;
	
	public Item(String item_id, String item_name, String item_size, String item_price, String item_category,
			String item_status, String item_wishlist, String item_offer_status) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_size = item_size;
		this.item_price = item_price;
		this.item_category = item_category;
		this.item_status = item_status;
		this.item_wishlist = item_wishlist;
		this.item_offer_status = item_offer_status;
	}
	
	
	public static void uploadItem(String item_name, String item_size, String item_price) {
		
	}
	
	public static void editItem(String item_id, String item_name, String item_category,
			String item_size, String item_price) {
		
	}
	
	public static void deleteItem(String item_id) {
		
	}
	
	public static void browseItem(String item_name) {
		
	}
	
	public static void viewItem() {
		
	}
	
	public static void checkItemValidation(String item_name, String item_category, 
			String item_size, String item_price) {
		
	}

	public static void viewRequestedItem(String item_id, String item_status) {
		
	}
	
	public static void offerPrice(String item_id, String item_price) {
		
	}
	
	public static void acceptOffer(String item_id) {
		
	}
	
	public static void declineOffer(String item_id) {
		
	}
	
	public static void approveItem(String item_id) {
		
	}
	
	public static void declineItem(String item_id) {
		
	}
	
	public static void viewAcceptedItem(String item_id) {
		
	}
	
	public static void viewOfferItem(String user_id) {
		
	}

	// Getter and Setter
	
	public String getItem_id() {
		return item_id;
	}


	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}


	public String getItem_name() {
		return item_name;
	}


	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}


	public String getItem_size() {
		return item_size;
	}


	public void setItem_size(String item_size) {
		this.item_size = item_size;
	}


	public String getItem_price() {
		return item_price;
	}


	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}


	public String getItem_category() {
		return item_category;
	}


	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}


	public String getItem_status() {
		return item_status;
	}


	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}


	public String getItem_wishlist() {
		return item_wishlist;
	}


	public void setItem_wishlist(String item_wishlist) {
		this.item_wishlist = item_wishlist;
	}


	public String getItem_offer_status() {
		return item_offer_status;
	}


	public void setItem_offer_status(String item_offer_status) {
		this.item_offer_status = item_offer_status;
	}
	

	
	
}
