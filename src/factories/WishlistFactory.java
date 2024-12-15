package factories;

import models.Wishlist;

public class WishlistFactory {

	
	public WishlistFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Wishlist createWishlist() {
		return new Wishlist();
	}
	
	public static Wishlist createWishlist(String wishlist_id, String product_id, String user_id) {
		return new Wishlist(wishlist_id, product_id, user_id);
	}
}
