package models;

import java.math.BigDecimal;
import java.util.ArrayList;


import factories.ItemFactory;
import factories.OfferFactory;
import factories.ProductFactory;
import services.Response;
import utils.GenerateID;

public class Item extends Model {

	private String item_id;
	private String item_name;
	private String item_size;
	private BigDecimal item_price;
	private String item_category;
	private String item_status;
	private String reason;
	
	
	private final String tableName = "items";
	private final String primaryKey = "item_id";
	
	
	public Item() {
		
	}
	
	public Item(String item_id, String item_name, String item_size, BigDecimal item_price, String item_category,
			String item_status, String reason) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_size = item_size;
		this.item_price = item_price;
		this.item_category = item_category;
		this.item_status = item_status;
		this.reason = reason;
	}
	
	
	/**
	 * VIEW ITEM
	 * [BUYER]
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewItem(){
		
		Response<ArrayList<Product>> res = new Response<ArrayList<Product>>();
		
		try {
			// Retrieve all products using the ProductFactory
			ArrayList<Product> productList = ProductFactory.createProduct().all();
			ArrayList<String> id = new ArrayList<String>();
			
			for (Product product : productList) {
				
				Item item = product.item();
				
				// Check if the item is approved
				if(item.getItem_status().equals("Approved")) {
					id.add(product.getProduct_id());
				}
			}
			
			// Retrieve products whose product_id is in the list of approved IDs
			productList = ProductFactory.createProduct().whereIn("product_id", id);
			
			res.setMessages("Success: Fetched all items");
			res.setIsSuccess(true);
			res.setData(productList);
			return res;
		} catch (Exception e) {
			
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	/**
	 * BROWSE ITEM
	 * [BUYER]
	 * @param Item_name
	 * @return
	 */
	public static Response<ArrayList<Product>> BrowseItem(String Item_name){
		Response<ArrayList<Product>> res = new Response<ArrayList<Product>>();
		
		try {
			
			ArrayList<Product> productList = ProductFactory.createProduct().all();
			ArrayList<String> id = new ArrayList<String>();
			
			
			for (Product product : productList) {
				// Get the associated Item object from the product
				Item item = product.item();
				
				
				if(item.getItem_status().equals("Approved") && item.getItem_name().toLowerCase().contains(Item_name.toLowerCase())) {
					id.add(product.getProduct_id());
				}
				
			}
			
			productList = ProductFactory.createProduct().whereIn("Product_id", id);
			
			res.setMessages("Success: Fetched browsed items");
			res.setIsSuccess(true);
			res.setData(productList);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}

	/**
	 * VIEW SELLER ITEM
	 * [SELLER]
	 * @param Seller_id
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewSellerItem(String Seller_id){
		Response<ArrayList<Product>> res = new Response<ArrayList<Product>>();
		
		try {
			ArrayList<Product> productList = ProductFactory.createProduct().where("Seller_id", "=", Seller_id);
			
			res.setMessages("Success: Fetched all seller items");
			res.setIsSuccess(true);
			res.setData(productList);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	
	/**
	 * VIEW REQUESTED ITEM
	 * [ADMIN]
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewRequestItem() {
	    Response<ArrayList<Product>> res = new Response<>();

	    try {
	    	
	        ArrayList<Product> productList = ProductFactory.createProduct().all();
	        ArrayList<String> id = new ArrayList<>();

	        for (Product product : productList) {
	        	
	        	// Check Requested item have Pending status
	            if (product.item().getItem_status().equals("Pending")) {
	                id.add(product.getProduct_id());
	            }
	        }

	        productList = ProductFactory.createProduct().whereIn("Product_id", id);

	        res.setMessages("Success: Fetched all requested items");
	        res.setIsSuccess(true);
	        res.setData(productList);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}

	/**
	 * VIEW ACCEPTED ITEM
	 * [BUYER]
	 * @return
	 */
	public static Response<ArrayList<Product>> ViewAcceptedItem() {
	    Response<ArrayList<Product>> res = new Response<ArrayList<Product>>();

	    try {
	        ArrayList<Product> productList = ProductFactory.createProduct().all();
	        ArrayList<String> ids = new ArrayList<>();

	        for (Product product : productList) {
	            if (product.item().getItem_status().equals("Approved")) {
	                ids.add(product.getProduct_id());
	            }
	        }

	        productList = ProductFactory.createProduct().whereIn("Product_id", ids);

	        res.setMessages("Success: Fetched all approved items");
	        res.setIsSuccess(true);
	        res.setData(productList);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	/**
	 * VIEW OFFERED ITEMS
	 * [SELLER]
	 * @param User_id
	 * @return
	 */
	public static Response<ArrayList<Offer>> ViewOfferItem(String User_id) {
	    Response<ArrayList<Offer>> res = new Response<>();

	    try {
	        ArrayList<Product> productList = ProductFactory.createProduct().where("Seller_id", "=", User_id);
	        ArrayList<Offer> offerList = new ArrayList<>();

	        for (Product product : productList) {
	            for (Offer offer : product.offers()) {
	                offerList.add(offer);
	            }
	        }

	        res.setMessages("Success: Fetched all offered items");
	        res.setIsSuccess(true);
	        res.setData(offerList);
	        
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
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
	public static Response<Item> UploadItem(String Seller_id,String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<Item> res = new Response<Item>();
		
		try {
			
			// Validate all values
			res = Item.CheckItemValidation(Item_name, Item_category, Item_size, Item_price);
			
			if(!res.getIsSuccess()) {
				return res;				
			}
			
			// Create Item using Factory and insert into database
			Item item = ItemFactory.createItem(GenerateID.generateNewId(ItemFactory.createItem().latest().getItem_id(), "IT"), 
					Item_name, Item_size, Item_price, Item_category, "Pending", null);
			
			item.insert();
			
			// Create Product using Factory and insert into database
			Product product = ProductFactory.createProduct(GenerateID.generateNewId(ProductFactory.createProduct().latest().getProduct_id(), "PR"), item.getItem_id(), Seller_id);
			product.insert();
		
		
		
			res.setMessages("Success: Item Uploaded");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
			
			
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
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
	public static Response<Item>  EditItem(String Item_id, String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<Item> res = new Response<Item>();
		
		try {
			res = Item.CheckItemValidation(Item_id, Item_name, Item_category, Item_size, Item_price);

			if(!res.getIsSuccess()) {
				return res;
			}
			
			// Update old value with a new one
			Item item = res.getData();
			item.setItem_name(Item_name);
			item.setItem_category(Item_category);
			item.setItem_size(Item_size);
			item.setItem_price(Item_price);
			
			// Update into database
			item.update(item.getItem_id());
			
			System.out.println(item.getItem_name());
			System.out.println(item.getItem_category());
			System.out.println(item.getItem_size());
			System.out.println(item.getItem_price());
			
			res.setMessages("Success: Item Updated");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	
	/**
	 * DELETE ITEM
	 * [SELLER]
	 * @param Item_id
	 * @return
	 */
	public static Response<Item>  DeleteItem(String Item_id) {
		Response<Item> res = new Response<Item>();
		
		try {
			
			Boolean item = ItemFactory.createItem().find(Item_id).delete(Item_id);
			
			if(!item) {
				res.setMessages("Error: Failed deletion");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}
			
			res.setMessages("Success: Item has been deleted");
			res.setIsSuccess(true);
			res.setData(null);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }		
	}
	
	/**
	 * OFFER PRICE 
	 * [BUYER]
	 * @param Product_id
	 * @param Buyer_id
	 * @param Item_price
	 * @return
	 */
	public static Response<Offer> OfferPrice(String Product_id, String Buyer_id, BigDecimal Item_price) {
	    Response<Offer> res = new Response<Offer>();

	    try {
	    	
	    	// Validating Product ID and price 
	        res = CheckItemValidation(Product_id, Item_price);
	        if (!res.getIsSuccess()) {
	            return res;
	        }

	        ArrayList<Offer> offers = OfferFactory.createOffer().where("Product_id", "=", Product_id);
	        
	        Offer buyerOffer = null;
	        for (Offer offer : offers) {
	            if (offer.getBuyer_id().equals(Buyer_id)) {
	                buyerOffer = offer;
	                break;
	            }
	        }

	        if (buyerOffer == null) {
	        	
	        	// Offer created and insert into database
	            buyerOffer = OfferFactory.createOffer(GenerateID.generateNewId(OfferFactory.createOffer().latest().getOffer_id(), "OF"),
	                    Product_id, Buyer_id, Item_price, "Offered", null);

	            buyerOffer.insert();
	        } else {
	        	
	        	// Validate if new offer price is lower than highest offer
	            if (buyerOffer.getItem_offer_price().compareTo(Item_price) >= 0) {
	                res.setMessages("Item Price Cannot Be Lower Than The Highest Offer");
	                res.setIsSuccess(false);
	                res.setData(null);
	                return res;
	            }

	            buyerOffer.setItem_offer_price(Item_price);
	            buyerOffer.update(buyerOffer.getOffer_id());
	        }

	        res.setMessages("Success: Item Offered!");
	        res.setIsSuccess(true);
	        res.setData(buyerOffer);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	/**
	 * ACCEPT OFFER
	 * [SELLER]
	 * @param Offer_id
	 * @return
	 */
	public static Response<Offer> AcceptOffer(String Offer_id) {
	    Response<Offer> res = new Response<>();

	    try {
	        Offer offer = OfferFactory.createOffer().find(Offer_id);

	        if (offer == null) {
	            res.setMessages("Error: Offer Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }

	        // Update offer status to be accepted
	        offer.setItem_offer_status("Accepted");
	        offer.update(Offer_id);

	        // Automatically purchase the item for buyer
	        Transaction.PurchaseItem(offer.getBuyer_id(), offer.getProduct_id());
	        
	        res.setMessages("Success: Offer accepted");
	        res.setIsSuccess(true);
	        res.setData(offer);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	
	/**
	 * APPROVE ITEM
	 * [ADMIN]
	 * @param Item_id
	 * @return
	 */
	public static Response<Item> ApproveItem(String Item_id) {
	    Response<Item> res = new Response<>();

	    try {
	        Item item = ItemFactory.createItem().find(Item_id);

	        if (item == null) {
	            res.setMessages("Error: Item Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }

	        item.setItem_status("Approved");
	        item.update(Item_id);

	        res.setMessages("Success: Item Approved!");
	        res.setIsSuccess(true);
	        res.setData(item);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
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

	    try {
	        Offer offer = OfferFactory.createOffer().find(Offer_id);
	        
	        if (offer == null) {
	            res.setMessages("Error: Offer Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }

	        // Update offer status to be declined
	        offer.setItem_offer_status("Declined");
	        offer.setReason(Reason);
	        offer.update(Offer_id);

	        res.setMessages("Success: Offer Declined!");
	        res.setIsSuccess(true);
	        res.setData(offer);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
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

	    try {
	        Item item = ItemFactory.createItem().find(Item_id);

	        if (item == null) {
	            res.setMessages("Error: Item Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }

	        item.setItem_status("Declined");
	        item.setReason(Reason);
	        item.update(Item_id);

	        res.setMessages("Success: Item Declined!");
	        res.setIsSuccess(true);
	        res.setData(item);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}

	/**
	 * VALIDATE ITEM
	 * @param Product_id
	 * @param Item_price
	 * @return
	 */
	public static Response<Offer> CheckItemValidation(String Product_id, BigDecimal Item_price) {
	    Response<Offer> res = new Response<>();
	    Product product = ProductFactory.createProduct().find(Product_id);

	    if (product == null) {
	        res.setMessages("Error: Product not found");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_price == null) {
	        res.setMessages("Error: Item price cannot be empty");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_price.compareTo(BigDecimal.ZERO) <= 0) {
	        res.setMessages("Error: item price cannot be 0!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }

	    res.setMessages("Success: Item Validated!");
	    res.setIsSuccess(true);
	    res.setData(null);
	    return res;
	}

	/**
	 * VALIDATE ITEM
	 * @param Item_name
	 * @param Item_category
	 * @param Item_size
	 * @param Item_price
	 * @return
	 */
	public static Response<Item> CheckItemValidation(String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
	    Response<Item> res = new Response<>();

	    if (Item_name.isEmpty()) {
	        res.setMessages("Error: Item name cannot be empty!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_name.length() < 3) {
	        res.setMessages("Error: item name must at least ne 3 character long!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_category.isEmpty()) {
	        res.setMessages("Error: Item category cannot be empty!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_category.length() < 3) {
	        res.setMessages("Error: Item category must at least 3 character long!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_size.isEmpty()) {
	        res.setMessages("Error: Item size cannot be empty!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_price == null) {
	        res.setMessages("Error: Item price cannot be empty!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } else if (Item_price.compareTo(BigDecimal.ZERO) <= 0) {
	        res.setMessages("Error: Item price cannot be 0!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }

	    res.setMessages("Success: Item Validated!");
	    res.setIsSuccess(true);
	    res.setData(null);
	    return res;
	}
	
	/**
	 * VALIDATE ITEM
	 * @param Item_id
	 * @param Item_name
	 * @param Item_category
	 * @param Item_size
	 * @param Item_price
	 * @return
	 */
	public static Response<Item> CheckItemValidation(String Item_id, String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<Item> res = new Response<Item>();
		Item item = ItemFactory.createItem().find(Item_id);
		
		if(item == null) {
			res.setMessages("Error: Item Not Found!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_name.isEmpty()) {
			res.setMessages("Error: Item Name Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_name.length() < 3) {
			res.setMessages("Error: Item Name Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.isEmpty()) {
			res.setMessages("Error: Item Category Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.length() < 3) {
			res.setMessages("Error: Item Category Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_size.isEmpty()) {
			res.setMessages("Error: Item Size Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price == null) {
			res.setMessages("Error: Item Price Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price.compareTo(BigDecimal.ZERO) <= 0) {
			res.setMessages("Error: Item Price Cannot Be 0!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		res.setMessages("Success: Item Validated!");
		res.setIsSuccess(true);
		res.setData(item);
		return res;
	}

	

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_size() {
		return item_size;
	}

	public void setItem_size(String item_size) {
		this.item_size = item_size;
	}

	public BigDecimal getItem_price() {
		return item_price;
	}

	public void setItem_price(BigDecimal item_price) {
		this.item_price = item_price;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}

	public String getItem_status() {
		return item_status;
	}

	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	protected String getTablename() {
		// TODO Auto-generated method stub
		return tableName;
	}

	@Override
	protected String getPrimarykey() {
		// TODO Auto-generated method stub
		return primaryKey;
	}
	
	public ArrayList<Product> product() {
		return this.hasMany(Product.class, "products", this.getItem_id(), "Item_id");
	}
	
	public ArrayList<Item> all(){
		return super.all(Item.class);
	}
	
	public ArrayList<Item> where(String columnName, String operator, String key){
		return super.where(Item.class, columnName, operator, key);
	}
	
	public Item update(String fromKey) {
		return super.update(Item.class, fromKey);
	}
	
	public Item insert() {
		return super.insert(Item.class);
	}
	
	public Item find(String fromKey) {
		return super.find(Item.class, fromKey);
	}
	
	public Item latest() {
		return super.latest(Item.class);
	}
	
	public Boolean delete(String fromKey) {
		return super.delete(Item.class, fromKey);
	}
	
	public ArrayList<Item> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(Item.class, columnName, listValues);
	}

	
	
}
