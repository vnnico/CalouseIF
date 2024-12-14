package factories;

import models.Item;
import java.math.BigDecimal;


public class ItemFactory {

	
	public ItemFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Item createItem() {
		return new Item();
	}
	
	public static Item createItem(String item_id, String item_name, String item_size, BigDecimal item_price, String item_category, String item_status, String reason) {
		return new Item(item_id, item_name, item_size, item_price, item_category, item_status, reason);
	}
}
