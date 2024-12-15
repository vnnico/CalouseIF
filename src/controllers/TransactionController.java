package controllers;

import java.util.ArrayList;

import models.Transaction;
import services.Response;

public class TransactionController {

	public Response<Transaction> PurchaseItem(String User_id, String Item_id) {
		return Transaction.PurchaseItem(User_id, Item_id);
	}
	
	public Response<ArrayList<Transaction>> ViewHistory(String User_id){
		return Transaction.ViewHistory(User_id);
	}
	
	public TransactionController() {
		// TODO Auto-generated constructor stub
	}
}
