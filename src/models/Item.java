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
	
	
	
}
