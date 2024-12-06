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
	
	
	
}
