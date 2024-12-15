package factories;

import models.Product;

public class ProductFactory {

	public ProductFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Product createProduct() {
		return new Product();
	}
	
	public static Product createProduct(String product_id, String item_id, String seller_id) {
		return new Product(product_id, item_id, seller_id);
	}
}
