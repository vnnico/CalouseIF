package models;

import java.util.*;

public class Product extends Model {

	private String product_id;
	private String item_id;
	private String seller_id;
	
	private final String tableName = "products";
	private final String primaryKey = "product_id";
	
	public Product() {
		
	}

	public Product(String product_id, String item_id, String seller_id) {
		super();
		this.product_id = product_id;
		this.item_id = item_id;
		this.seller_id = seller_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getTablename() {
		return tableName;
	}

	public String getPrimarykey() {
		return primaryKey;
	}
	
	public User user() {
	    return this.hasOne(User.class, "users", this.seller_id, "User_id");
	}

	public Item item() {
	    return this.hasOne(Item.class, "items", this.item_id, "Item_id");
	}

	public ArrayList<Wishlist> wishlists() {
	    return this.hasMany(Wishlist.class, "wishlists", this.product_id, "Product_id");
	}

	public ArrayList<Offer> offers() {
	    return this.hasMany(Offer.class, "offers", this.product_id, "Product_id");
	}

	public ArrayList<Transaction> transactions() {
	    return this.hasMany(Transaction.class, "transactions", this.product_id, "Product_id");
	}

	public ArrayList<Product> all() {
	    return super.all(Product.class);
	}

	public ArrayList<Product> where(String columnName, String operator, String key) {
	    return super.where(Product.class, columnName, operator, key);
	}

	public Product update(String fromKey) {
	    return super.update(Product.class, fromKey);
	}

	public Product insert() {
	    return super.insert(Product.class);
	}

	public Product find(String fromKey) {
	    return super.find(Product.class, fromKey);
	}

	public Product latest() {
	    return super.latest(Product.class);
	}

	public Boolean delete(String fromKey) {
	    return super.delete(Product.class, fromKey);
	}

	public ArrayList<Product> whereIn(String columnName, ArrayList<String> listValues) {
	    return super.whereIn(Product.class, columnName, listValues);
	}

	
}
