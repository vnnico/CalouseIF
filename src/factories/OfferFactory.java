package factories;

import models.Offer;
import java.math.*;

public class OfferFactory {

	public OfferFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Offer createOffer() {
		return new Offer();
	}

	public static Offer createOffer(String offer_id, String product_id, String buyer_id, BigDecimal item_offer_price, String item_offer_status, String reason) {
		return new Offer(offer_id, product_id, buyer_id, item_offer_price, item_offer_status, reason);
	}
}
