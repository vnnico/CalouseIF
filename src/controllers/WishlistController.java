package controllers;

import java.util.ArrayList;

import models.Wishlist;
import services.Response;

public class WishlistController {
	
	public WishlistController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * VIEW WISHLIST
	 * [BUYER]
	 * @param User_id
	 * @return
	 */
	public static Response<ArrayList<Wishlist>> ViewWishlist(String User_id){
		return Wishlist.ViewWishlist(User_id);
	}
	
	/**
	 * ADD TO WISHLIST
	 * [BUYER]
	 * @param Product_id
	 * @param User_id
	 * @return
	 */
	public static Response<Wishlist> AddWishlist(String Product_id, String User_id) {
		return Wishlist.AddWishlist(Product_id, User_id);
	}
	
	/**
	 * REMOVE WISHLIST
	 * [BUYER]
	 * @param Wishlist_id
	 * @return
	 */
	public static Response<Wishlist> RemoveWishlist(String Wishlist_id) {
		return Wishlist.RemoveWishlist(Wishlist_id);
	}
	

}
