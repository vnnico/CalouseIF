package controllers;

import java.util.ArrayList;

import models.Transaction;
import services.Response;

public class TransactionController {
	
	public TransactionController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Purchase Item 
	 * [BUYER]
	 * @param User_id
	 * @param Item_id
	 * @return
	 */
	public static Response<Transaction> PurchaseItem(String User_id, String Item_id) {
		return Transaction.PurchaseItem(User_id, Item_id);
	}
	
	/**
	 * View Purchase History
	 * [BUYER]
	 * @param User_id
	 * @return
	 */
	public static Response<ArrayList<Transaction>> ViewHistory(String User_id){
		return Transaction.ViewHistory(User_id);
	}
	

}
