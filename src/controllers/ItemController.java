package controllers;

import models.Item;
import models.Offer;
import models.Product;
import services.Response;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
	
	
	public ItemController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * VIEW ALL ITEMS
	 * [BUYER]
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewItem(){
		return Item.ViewItem();
	}
	
	/**
	 * BROWSE ITEM 
	 * [BUYER]
	 * @param Item_name
	 * @return
	 */
	public static Response<ArrayList<Product>> BrowseItem(String Item_name){
		return Item.BrowseItem(Item_name);
	}
	

	/**
	 * UPLOAD ITEM
	 * [SELLER]
	 * @param Seller_id
	 * @param Item_name
	 * @param Item_category
	 * @param Item_size
	 * @param Item_price
	 * @return
	 */
	public static Response<Item> UploadItem(String Seller_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<Item> res = new Response<Item>();
		
		if(Item_name.isEmpty()) {
			res.setMessages("Item name cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_name.length() < 3) {
			res.setMessages("Iten name must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.isEmpty()) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.length() < 3) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_size.isEmpty()) {
			res.setMessages("Item size cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		try {
			 BigDecimal price = new BigDecimal(Item_price);
			if(price.compareTo(BigDecimal.ZERO) <= 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (NumberFormatException e) {
		
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return Item.UploadItem(Seller_id,Item_name, Item_category, Item_size, new BigDecimal(Item_price));
	}
	
	/**
	 * EDIT ITEM
	 * [SELLER]
	 * @param Item_id
	 * @param Item_name
	 * @param Item_category
	 * @param Item_size
	 * @param Item_price
	 * @return
	 */
	public static Response<Item> EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<Item> res = new Response<Item>();

		if(Item_name.isEmpty()) {
			res.setMessages("Item name cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_name.length() < 3) {
			res.setMessages("Iten name must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.isEmpty()) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.length() < 3) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_size.isEmpty()) {
			res.setMessages("Item size cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		try {
			 BigDecimal price = new BigDecimal(Item_price);
			if(price.compareTo(BigDecimal.ZERO) <= 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (NumberFormatException e) {
		
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return Item.EditItem(Item_id, Item_name, Item_category, Item_size, new BigDecimal(Item_price));
	}
	
	
	/**
	 * DELETE ITEM
	 * [SELLER]
	 * @param Item_id
	 * @return
	 */
	public static Response<Item> DeleteItem(String Item_id) {		
		return Item.DeleteItem(Item_id);
	}
	
	/**
	 * VIEW SELLER ITEM
	 * [SELLER]
	 * @param Seller_id
	 * @return
	 */
	public static ArrayList<Item> ViewSellerItem(String Seller_id){
		Response<ArrayList<Product>> res = Item.ViewSellerItem(Seller_id);
		ArrayList<Product> data = res.getData();
		
		// Temporary array to store seller items
		ArrayList<Item> seller_items = new ArrayList<Item>();
		for (Product product : data) {
			seller_items.add(product.item());
		}
		
		return seller_items;
	}

	/**
	 * VIEW REQUESTED ITEM
	 * [ADMIN]
	 * @param Item_status
	 * @return
	 */
	public static Response<ArrayList<Item>> ViewRequestItem(String Item_status){
		Response<ArrayList<Product>> res = Item.ViewRequestItem();
		ArrayList<Product> data = res.getData();
		// Temporary array to store seller items
		ArrayList<Item> item = new ArrayList<Item>();
		
		for (Product product : data) {
			item.add(product.item());
		}
		
		Response<ArrayList<Item>> resResult = new Response<ArrayList<Item>>();
		resResult.setMessages(res.getMessages());
		resResult.setIsSuccess(res.getIsSuccess());
		resResult.setData(item);
		return resResult;
	}
	
	/**
	 * VIEW ACCEPTED ITEM
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewAcceptedItem(){
		return Item.ViewAcceptedItem();
	}
	
	/**
	 * VIEW OFFERED ITEM
	 * [SELLER]
	 */
	public static Response<ArrayList<Offer>> ViewOfferItem(String Seller_id){
		return Item.ViewOfferItem(Seller_id);
	}
	
	/**
	 * OFFER PRICE 
	 * [BUYER]
	 * @param Product_id
	 * @param Buyer_id
	 * @param Item_price
	 * @return
	 */
	public static Response<Offer> OfferPrice(String Product_id, String Buyer_id, String Item_price) {
		Response<Offer> res = new Response<Offer>();
		
		// Validating Price 
		try {
			if(new BigDecimal(Item_price).compareTo(BigDecimal.ZERO) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (Exception e) {
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		return Item.OfferPrice(Product_id, Buyer_id, new BigDecimal(Item_price));
	}
	
	/**
	 * ACCEPT OFFER 
	 * [SELLER]
	 * @param Item_id
	 * @return
	 */
	public static Response<Offer> AcceptOffer(String Item_id) {
		return Item.AcceptOffer(Item_id);
	}
	
	/**
	 * DECLINE OFFER
	 * [SELLER]
	 * @param Offer_id
	 * @param Reason
	 * @return
	 */
	public static Response<Offer> DeclineOffer(String Offer_id, String Reason) {
		Response<Offer> res = new Response<Offer>();
		// Validate reason 
		if(Reason.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return Item.DeclineOffer(Offer_id, Reason);
	}

	/**
	 * APPROVE ITEM
	 * [ADMIN]
	 * @param Item_id
	 * @return
	 */
	public static Response<Item> ApproveItem(String Item_id) {
		return Item.ApproveItem(Item_id);
	}
	
	/**
	 * DECLINE ITEM
	 * [ADMIN]
	 * @param Item_id
	 * @param Reason
	 * @return
	 */
	public static Response<Item> DeclineItem(String Item_id, String Reason) {
		Response<Item> res = new Response<Item>();
		if(Reason.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return Item.DeclineItem(Item_id, Reason);
	}
	


}
