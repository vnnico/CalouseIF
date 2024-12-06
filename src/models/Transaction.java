package models;

public class Transaction {

	private String user_id;
	private String item_id;
	private String transaction_id;
	
	public Transaction(String user_id, String item_id, String transaction_id) {
		super();
		this.user_id = user_id;
		this.item_id = item_id;
		this.transaction_id = transaction_id;
	}
	
	public static void purchaseItems(String user_id, String item_id) {
		
	}
	
	public static void viewHistory(String user_id) {
		
	}
	
	public static void createTransaction(String transaction_id) {
		
	}
	
	// GETTER & SETTER

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	
}
