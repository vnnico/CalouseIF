package views.Buyer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.Offer;
import models.Product;
import services.Response;
import views.PageManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import controllers.ItemController;
import controllers.TransactionController;
import controllers.WishlistController;

public class BuyerHomePage implements EventHandler<ActionEvent> {

		private BorderPane borderPane; // Main layout container
	    private VBox headerContainer; // Header section container
	    private TableView<Product> tableView;

	    private Label header;
	    private TextField searchField;
	    private Button searchButton, clearButton;

	    private MenuBar menuBar;
	    private Menu menu;
	    private MenuItem wishlistMenuItem, purchaseHistoryMenuItem;

	    private Scene scene;
	    private PageManager pageManager;

	    public BuyerHomePage(PageManager pageManager) {
	        this.pageManager = pageManager;
	        initUI();
	        initMenu();
	        setLayout();
	        loadAllItems(); 	    }

	    private void initUI() {
	    	
	    	// Main layout  container
	        borderPane = new BorderPane();

	        // Header Text Label
	        header = new Label("Buyer Dashboard");
	        header.setStyle("-fx-font-size: 20px; ");

	        // Menu Bar consist of wishlist and purchase history page.
	        menuBar = new MenuBar();
	        menu = new Menu("Menu");
	        wishlistMenuItem = new MenuItem("Wishlist");
	        purchaseHistoryMenuItem = new MenuItem("Purchase History");

	        // Search and Clear Button
	        searchField = new TextField();
	        searchField.setPromptText("Search items...");
	        searchField.setPrefWidth(400);
	        searchButton = new Button("Search");
	        searchButton.setOnAction(e -> handleSearch());
	        
	        clearButton = new Button("Clear"); 
	        clearButton.setOnAction(e -> handleClear()); 

	        HBox searchBar = new HBox(10, searchField, searchButton, clearButton);
	        searchBar.setAlignment(Pos.CENTER_LEFT);
	        searchBar.setPadding(new Insets(10));

	        headerContainer = new VBox(menuBar, header, searchBar);
	        headerContainer.setSpacing(5);

	        borderPane.setTop(headerContainer);

	        initTable();
	        
	        scene = new Scene(borderPane, 1000, 600);
	    }

	    private void initTable() {
	        tableView = new TableView<>();
	        tableView.setPrefWidth(980);
	        tableView.setPlaceholder(new Label("No items available"));
	        

	        // Define and configure table columns
	        TableColumn<Product, String> idColumn = new TableColumn<>("Item ID");
	        idColumn.setCellValueFactory(param -> {
	            String id = param.getValue().item().getItem_id(); 
	            return new ReadOnlyObjectWrapper<>(id); 
	        });
	        

	        TableColumn<Product, String> nameColumn = new TableColumn<>("Item Name");
	        nameColumn.setCellValueFactory(param -> {
	            String itemName = param.getValue().item().getItem_name(); 
	            return new ReadOnlyObjectWrapper<>(itemName); 
	        });

	        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
	        categoryColumn.setCellValueFactory(param -> {
	            String category = param.getValue().item().getItem_category();
	            return new ReadOnlyObjectWrapper<>(category);
	        });

	        TableColumn<Product, String> sizeColumn = new TableColumn<>("Size");
	        sizeColumn.setCellValueFactory(param -> {
	            String size = param.getValue().item().getItem_size();
	            return new ReadOnlyObjectWrapper<>(size);
	        });

	        TableColumn<Product, BigDecimal> priceColumn = new TableColumn<>("Price");
	        priceColumn.setCellValueFactory(param -> {
	            BigDecimal price = param.getValue().item().getItem_price();
	            return new ReadOnlyObjectWrapper<>(price);
	        });

	        
	     
	        // Define action column with buttons
	        TableColumn<Product, Void> actionColumn = new TableColumn<>("Actions");
	        actionColumn.setCellFactory(param -> new TableCell<>() {
	            private final Button purchaseButton = new Button("Purchase");
	            private final Button offerButton = new Button("Offer Price");
	            private final Button wishlistButton = new Button("Add to Wishlist");
	            private final HBox pane = new HBox(purchaseButton, offerButton, wishlistButton);

	            {
	                pane.setSpacing(10);

	                // Attach event handlers to purchase, offer and wishlist button.
	                purchaseButton.setOnAction(event -> {
	                    Product product = getTableView().getItems().get(getIndex());
	                    handlePurchase(product);
	                });

	                offerButton.setOnAction(event -> {
	                    Product product = getTableView().getItems().get(getIndex());
	                    handleOfferPrice(product);
	                });

	                wishlistButton.setOnAction(event -> {
	                    Product product = getTableView().getItems().get(getIndex());
	                    handleAddToWishlist(product);
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                	// Clear cell content if empty
	                    setGraphic(null);
	                } else {
	                	// Set buttons for non-empty rows
	                    setGraphic(pane);
	                }
	            }
	        });
	        
	        // Set Width
	        idColumn.setMinWidth(100);
	        nameColumn.setMinWidth(150);
	        categoryColumn.setMinWidth(150);
	        sizeColumn.setMinWidth(100);
	        priceColumn.setMinWidth(100);
	        actionColumn.setMinWidth(300);
	        
	        tableView.getColumns().addAll(idColumn,nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);
	        borderPane.setCenter(tableView);
	    }

	    private void initMenu() {
	        menu.getItems().addAll(wishlistMenuItem, purchaseHistoryMenuItem);
	        menuBar.getMenus().add(menu);

	        wishlistMenuItem.setOnAction(event -> pageManager.showViewWishlist());
	        purchaseHistoryMenuItem.setOnAction(event -> pageManager.showViewPurchaseHistory());
	    }

	    private void setLayout() {
	       
	    }

	    private void loadAllItems() {

	    	// Fetch Items from db
	        Response<ArrayList<Product>> res = ItemController.ViewItem();
	        System.out.println(res.getData());
	        if (res.getIsSuccess()) {
            tableView.setItems(javafx.collections.FXCollections.observableArrayList(res.getData()));
	        } else {
	            showAlert(AlertType.ERROR, "Error", res.getMessages());
	        }
	    
	    }

	    private void handleSearch() {
	    	// Get search query
	        String query = searchField.getText().trim();
	        if (query.isEmpty()) {
	            loadAllItems();
	            return;
	        }

	        Response<ArrayList<Product>> res = ItemController.BrowseItem(query);
	        if (res.getIsSuccess()) {
	        	
	            tableView.setItems(javafx.collections.FXCollections.observableArrayList(res.getData()));
	            if (res.getData().isEmpty()) {
	            	
	                showAlert(AlertType.INFORMATION, "Search Results", "No items found for: " + query);
	            }
	        } else {
	            showAlert(AlertType.ERROR, "Search Failed", res.getMessages());
	        }
	    }

	    private void handleClear() {
	    	// Clear search field and reload all items.
	        searchField.clear(); 
	        loadAllItems(); 
	    }

	    private void handlePurchase(Product product) {
	        Item item = product.item();
	        
	        // Purchase Confirmation Alert
	        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
	        confirmationAlert.setTitle("Confirmation");
	        confirmationAlert.setHeaderText("Purchase Confirmation");
	        confirmationAlert.setContentText("Are you sure you want to purchase " + item.getItem_name() + "?");

	        Optional<ButtonType> result = confirmationAlert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	        	
	        	// Return current authenticated user
	            String userId = pageManager.getLoggedInUser().getUser_id();
	            
	            Response<models.Transaction> res;
			
	            // Handle transaction 
				res = TransactionController.PurchaseItem(userId, product.getProduct_id());
			
	            if (res.getIsSuccess()) {
	                showAlert(AlertType.INFORMATION, "Purchase Successful", "You have successfully purchased: " + item.getItem_name());
	                
	            } else {
	                showAlert(AlertType.ERROR, "Purchase Failed", res.getMessages());
	            }
	        }
	    }

	    private void handleOfferPrice(Product product) {
	      
	    	// Offer price input form will pop up
	        Dialog<String> dialog = new Dialog<>();
	        dialog.setTitle("Offer Price");
	        dialog.setHeaderText("Enter your offer price for " + product.item().getItem_name());
	        
	        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

	        TextField offerPriceField = new TextField();
	        offerPriceField.setPromptText("Offer Price");
	        
	        
	        VBox vbox = new VBox(10, new Label("Offer Price:"), offerPriceField);
	        vbox.setPadding(new Insets(10));
	        
	        dialog.getDialogPane().setContent(vbox);

	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == submitButtonType) {
	                return offerPriceField.getText().trim();
	            }
	            return null;
	        });

	        // Submit the offer price value
	        Optional<String> offerResult = dialog.showAndWait();
	        if (offerResult.isPresent()) {
	            String offerValue = offerResult.get();
	     
	            
	            String buyerId = pageManager.getLoggedInUser().getUser_id();
	            Response<Offer> res = ItemController.OfferPrice(product.getProduct_id(), buyerId, offerValue);
	            
	            
	            if (res.getIsSuccess()) {
	                showAlert(AlertType.INFORMATION, "Offer Submitted", "Your offer has been submitted successfully.");
	            } else {
	                showAlert(AlertType.ERROR, "Offer Failed", res.getMessages());
	            }
	        }
	    }

	    private void handleAddToWishlist(Product product) {
	    	
	    	// Wishlist Confirmation Pop UP
	        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
	        confirmationAlert.setTitle("Add to Wishlist");
	        confirmationAlert.setHeaderText("Confirm Wishlist Addition");
	        confirmationAlert.setContentText("Are you sure you want to add " + product.item().getItem_name() + " to your wishlist?");
	        
	        Optional<ButtonType> result = confirmationAlert.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            String userId = pageManager.getLoggedInUser().getUser_id();
	            
	            // Add item to wishlist 
	            Response<models.Wishlist> res = WishlistController.AddWishlist(product.getProduct_id(), userId);
	            if (res.getIsSuccess()) {
	                showAlert(AlertType.INFORMATION, "Wishlist Updated", product.item().getItem_name() + " has been added to your wishlist.");
	            } else {
	                showAlert(AlertType.ERROR, "Wishlist Failed", res.getMessages());
	            }
	        }
	    }

	    @Override
	    public void handle(ActionEvent event) {

	    }

	    public Scene getScene() {
	        return scene;
	    }

	    private void showAlert(AlertType type, String title, String content) {
	        Alert alert = new Alert(type);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
}
