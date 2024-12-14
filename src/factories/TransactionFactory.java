package factories;

import models.Transaction;

public class TransactionFactory {

	public TransactionFactory() {
	    // TODO Auto-generated constructor stub
	}

	public static Transaction createTransaction() {
	    return new Transaction();
	}

	public static Transaction createTransaction(String user_id, String product_id, String transaction_id) {
	    return new Transaction(user_id, product_id, transaction_id);
	}

}
