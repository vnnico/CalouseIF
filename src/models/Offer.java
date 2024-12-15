package models;

import java.util.*;
import java.math.*;

public class Offer extends Model {

    private String offer_id;
    private String product_id;
    private String buyer_id;
    private BigDecimal item_offer_price;
    private String item_offer_status;
    private String reason;
    
    private final String tableName = "offers";
	private final String primaryKey = "offer_id";

	public Offer() {
		
	}
	
    public Offer(String offer_id, String product_id, String buyer_id, BigDecimal item_offer_price,
                 String item_offer_status, String reason) {
        super();
        this.offer_id = offer_id;
        this.product_id = product_id;
        this.buyer_id = buyer_id;
        this.item_offer_price = item_offer_price;
        this.item_offer_status = item_offer_status;
        this.reason = reason;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public BigDecimal getItem_offer_price() {
        return item_offer_price;
    }

    public void setItem_offer_price(BigDecimal item_offer_price) {
        this.item_offer_price = item_offer_price;
    }

    public String getItem_offer_status() {
        return item_offer_status;
    }

    public void setItem_offer_status(String item_offer_status) {
        this.item_offer_status = item_offer_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    protected String getTablename() {
        // TODO: Update this method to return the correct table name
        return tableName;
    }

    @Override
    protected String getPrimarykey() {
        // TODO: Update this method to return the correct primary key
        return primaryKey;
    }

    public Product product() {
        return this.hasOne(Product.class, "products", this.getProduct_id(), "Product_id");
    }

    public User user() {
        return this.hasOne(User.class, "users", this.getBuyer_id(), "User_id");
    }

    public ArrayList<Offer> all() {
        return super.all(Offer.class);
    }

    public ArrayList<Offer> where(String columnName, String operator, String key) {
        return super.where(Offer.class, columnName, operator, key);
    }

    public Offer update(String fromKey) {
        return super.update(Offer.class, fromKey);
    }

    public Offer insert() {
        return super.insert(Offer.class);
    }

    public Offer find(String fromKey) {
        return super.find(Offer.class, fromKey);
    }

    public Offer latest() {
        return super.latest(Offer.class);
    }

    public Boolean delete(String fromKey) {
        return super.delete(Offer.class, fromKey);
    }

    public ArrayList<Offer> whereIn(String columnName, ArrayList<String> listValues) {
        return super.whereIn(Offer.class, columnName, listValues);
    }
}
