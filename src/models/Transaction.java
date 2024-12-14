package models;

import java.util.*;

public class Transaction extends Model{

	private String user_id;
	private String product_id;
	private String transaction_id;
	
	private final String Tablename = "transactions";
	private final String Primarykey = "Transaction_id";
	
	public Transaction(String user_id, String product_id, String transaction_id) {
		super();
		this.user_id = user_id;
		this.product_id = product_id;
		this.transaction_id = transaction_id;
	}

	
	// GETTER & SETTER

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	public String getTablename() {
	    return Tablename;
	}

	public String getPrimarykey() {
	    return this.Primarykey;
	}

	public Product product() {
	    return this.hasOne(Product.class, "products", this.getProduct_id(), "Product_id");
	}

	public User user() {
	    return this.hasOne(User.class, "users", this.getUser_id(), "User_id");
	}

	public ArrayList<Transaction> all() {
	    return super.all(Transaction.class);
	}

	public ArrayList<Transaction> where(String columnName, String operator, String key) {
	    return super.where(Transaction.class, columnName, operator, key);
	}

	public Transaction update(String fromKey) {
	    return super.update(Transaction.class, fromKey);
	}

	public Transaction insert() {
	    return super.insert(Transaction.class);
	}

	public Transaction find(String fromKey) {
	    return super.find(Transaction.class, fromKey);
	}

	public Transaction latest() {
	    return super.latest(Transaction.class);
	}

	public Boolean delete(String fromKey) {
	    return super.delete(Transaction.class, fromKey);
	}

	public ArrayList<Transaction> whereIn(String columnName, ArrayList<String> listValues) {
	    return super.whereIn(Transaction.class, columnName, listValues);
	}

	
	
}
