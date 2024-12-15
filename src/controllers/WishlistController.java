package controllers;

import java.util.ArrayList;

import models.Wishlist;
import services.Response;

public class WishlistController {

	public static Response<ArrayList<Wishlist>> ViewWishlist(String User_id){
		return Wishlist.ViewWishlist(User_id);
	}
	
	public static Response<Wishlist> AddWishlist(String Product_id, String User_id) {
		return Wishlist.AddWishlist(Product_id, User_id);
	}
	
	public static Response<Wishlist> RemoveWishlist(String Wishlist_id) {
		return Wishlist.RemoveWishlist(Wishlist_id);
	}
	
	public WishlistController() {
		// TODO Auto-generated constructor stub
	}
}
