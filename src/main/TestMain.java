package main;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;



public class TestMain extends Application {
	
	Scene Homepage_Buyer;
	BorderPane borderContainer;
	GridPane gridContainer;
	FlowPane flowContainer;	
	
	Label IdLbl, ItemNameLbl, GreetingsLbl, ItemCategoryLbl, ItemSizeLbl, ItemPriceLbl, ActionLbl, TransactionIDLbl;
	
	Button PurchaseBtn, OfferBtn, WishlistBtn, RemoveBtn, OKBtn, CancelBtn;
	
	public void initialize() {
		
		borderContainer = new BorderPane();
		gridContainer = new GridPane();
		flowContainer = new FlowPane();
		
		IdLbl = new Label("ID");
		ItemNameLbl = new Label("Item Name");
		ItemCategoryLbl = new Label("Item Category");
		ItemPriceLbl = new Label("Item Price");
		ActionLbl = new Label("Action");
		TransactionIDLbl = new Label("Transaction ID");
		
		PurchaseBtn = new Button("Purchase");
		OfferBtn = new Button("Offer");
		WishlistBtn = new Button("Add to Wishtlist");
		RemoveBtn = new Button("Remove");
		OKBtn = new Button("OK");
		CancelBtn = new Button("Cancel");
		
		Homepage_Buyer = new Scene(borderContainer, 1000, 600);
	}
	
	private void handlePurchase(String itemName) {
	    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationAlert.setTitle("Confirmation");
	    confirmationAlert.setHeaderText("Purchase Confirmation");
	    confirmationAlert.setContentText("Are you sure you want to purchase " + itemName + "?");

	    Optional<ButtonType> result = confirmationAlert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        System.out.println("Purchase confirmed for: " + itemName);
	    } else {
	        System.out.println("Purchase canceled for: " + itemName);
	    }
	}

	private void handleAddToWishlist(String itemName) {
	    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationAlert.setTitle("Confirmation");
	    confirmationAlert.setHeaderText("Add to Wishlist");
	    confirmationAlert.setContentText("Are you sure you want to add " + itemName + " to your wishlist?");

	    Optional<ButtonType> result = confirmationAlert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        System.out.println("Item added to wishlist: " + itemName);
	    } else {
	        System.out.println("Adding to wishlist canceled for: " + itemName);
	    }
	}
	
	private void handleOffer(String itemName) {
	    Stage offerStage = new Stage();
	    offerStage.setTitle("Make an Offer");

	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(20));
	    grid.setHgap(10);
	    grid.setVgap(10);

	    Label offerPriceLabel = new Label("Offer Price:");
	    TextField offerPriceField = new TextField();
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);

	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(e -> {
	        String offerPrice = offerPriceField.getText();
	        if (offerPrice.isEmpty() || !offerPrice.matches("\\d+")) {
	            errorLabel.setText("Please enter a valid price.");
	        } else {
	            System.out.println("Offer submitted for " + itemName + ": " + offerPrice);
	            offerStage.close();
	        }
	    });

	    grid.add(offerPriceLabel, 0, 0);
	    grid.add(offerPriceField, 1, 0);
	    grid.add(errorLabel, 1, 1);
	    grid.add(submitButton, 1, 2);


	    Scene scene = new Scene(grid, 300, 150);
	    offerStage.setScene(scene);
	    offerStage.show();
	}
	
	private Scene getWishlistScene(Stage stage) {
	    HBox header = new HBox();
	    header.setPadding(new Insets(10));
	    header.setSpacing(10);
	    header.setBackground(new Background(new BackgroundFill(
	        Color.LIGHTGRAY, 
	        CornerRadii.EMPTY, 
	        Insets.EMPTY 
	    )));
	    
	    BorderPane wishlistLayout = new BorderPane();
	    
	    MenuButton menuButton = new MenuButton("Menu");
	    MenuItem homepageItem = new MenuItem("Homepage");
	    MenuItem purchaseHistoryItem = new MenuItem("Purchase History");

	    homepageItem.setOnAction(e -> stage.setScene(Homepage_Buyer));
	    purchaseHistoryItem.setOnAction(e -> stage.setScene(getPurchaseHistoryScene(stage)));

	    menuButton.getItems().addAll(homepageItem, purchaseHistoryItem);

	    header.getChildren().add(menuButton);
	    wishlistLayout.setTop(header);
	    
	    GridPane wishlistTable = new GridPane();
	    wishlistTable.setPadding(new Insets(20));
	    wishlistTable.setHgap(10);
	    wishlistTable.setVgap(10);
	    wishlistTable.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));
	    
	    Label idHeader = new Label("ID");
	    Label itemNameHeader = new Label("Item Name");
	    Label itemCategoryHeader = new Label("Item Category");
	    Label itemSizeHeader = new Label("Item Size");
	    Label itemPriceHeader = new Label("Item Price");
	    Label actionHeader = new Label("Action");
	    
	    
	    wishlistTable.add(idHeader, 0, 0);
	    wishlistTable.add(itemNameHeader, 1, 0);
	    wishlistTable.add(itemCategoryHeader, 2, 0);
	    wishlistTable.add(itemSizeHeader, 3, 0);
	    wishlistTable.add(itemPriceHeader, 4, 0);
	    wishlistTable.add(actionHeader, 5, 0);

	    GridPane.setMargin(idHeader, new Insets(10, 10, 10, 10));
	    GridPane.setMargin(itemNameHeader, new Insets(10, 70, 10, 70));
	    GridPane.setMargin(itemCategoryHeader, new Insets(10, 30, 10, 30));
	    GridPane.setMargin(itemSizeHeader, new Insets(10, 30, 10, 30));
	    GridPane.setMargin(itemPriceHeader, new Insets(10, 30, 10, 30));
	    GridPane.setMargin(actionHeader, new Insets(10, 100, 10, 100));
	    
	    for (int i = 1; i <= 50; i++) {
	        Label rowId = new Label(String.valueOf(i));
	        Label rowItemName = new Label("Item Name " + i);
	        Label rowItemCategory = new Label("Item Category");
	        Label rowItemSize = new Label("Item Size");
	        Label rowItemPrice = new Label("Item Price");
	        Button removeButton = new Button("Remove");

	        removeButton.setOnAction(e -> {
	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Confirmation");
	            confirmationAlert.setHeaderText("Remove Item");
	            confirmationAlert.setContentText("Are you sure you want to remove " + rowItemName.getText() + " from the wishlist?");
	            Optional<ButtonType> result = confirmationAlert.showAndWait();
	            if (result.isPresent() && result.get() == ButtonType.OK) {
	                System.out.println("Removed from wishlist: " + rowItemName.getText());
	            }
	        });
	        
	        GridPane.setHalignment(removeButton, HPos.CENTER);

	        wishlistTable.add(rowId, 0, i);
	        wishlistTable.add(rowItemName, 1, i);
	        wishlistTable.add(rowItemCategory, 2, i);
	        wishlistTable.add(rowItemSize, 3, i);
	        wishlistTable.add(rowItemPrice, 4, i);
	        wishlistTable.add(removeButton, 5, i);

	        GridPane.setMargin(rowId, new Insets(10, 10, 10, 10));
	        GridPane.setMargin(rowItemName, new Insets(10, 10, 10, 10));
	        GridPane.setMargin(rowItemCategory, new Insets(10, 10, 10, 10));
	        GridPane.setMargin(rowItemSize, new Insets(10, 10, 10, 10));
	        GridPane.setMargin(rowItemPrice, new Insets(10, 10, 10, 10));
	        GridPane.setMargin(removeButton, new Insets(10, 10, 10, 10));
	    }

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(wishlistTable);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPadding(new Insets(10));

	    wishlistLayout.setCenter(scrollPane);

	    return new Scene(wishlistLayout, 1000, 600);
	}



	private Scene getPurchaseHistoryScene(Stage stage) {
	    BorderPane purchaseHistoryLayout = new BorderPane();

	    // Header
	    HBox header = new HBox();
	    header.setPadding(new Insets(10));
	    header.setSpacing(10);
	    header.setBackground(new Background(new BackgroundFill(
	        Color.LIGHTGRAY,
	        CornerRadii.EMPTY,
	        Insets.EMPTY
	    )));
	    MenuButton menuButton = new MenuButton("Menu");
	    MenuItem homepageItem = new MenuItem("Homepage");
	    MenuItem wishlistItem = new MenuItem("Wishlist");

	    homepageItem.setOnAction(e -> stage.setScene(Homepage_Buyer));
	    wishlistItem.setOnAction(e -> stage.setScene(getWishlistScene(stage)));

	    menuButton.getItems().addAll(homepageItem, wishlistItem);

	    header.getChildren().add(menuButton);
	    purchaseHistoryLayout.setTop(header);

	    GridPane historyTable = new GridPane();
	    historyTable.setPadding(new Insets(20));
	    historyTable.setHgap(10);
	    historyTable.setVgap(10);
	    historyTable.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null)));

	    Label transactionIdHeader = new Label("Transaction ID");
	    Label itemNameHeader = new Label("Item Name");
	    Label itemCategoryHeader = new Label("Item Category");
	    Label itemSizeHeader = new Label("Item Size");
	    Label itemPriceHeader = new Label("Item Price");

	    historyTable.add(transactionIdHeader, 0, 0);
	    historyTable.add(itemNameHeader, 1, 0);
	    historyTable.add(itemCategoryHeader, 2, 0);
	    historyTable.add(itemSizeHeader, 3, 0);
	    historyTable.add(itemPriceHeader, 4, 0);

	    GridPane.setMargin(transactionIdHeader, new Insets(10));
	    GridPane.setMargin(itemNameHeader, new Insets(10, 70, 10, 70));
	    GridPane.setMargin(itemCategoryHeader, new Insets(10, 30, 10, 30));
	    GridPane.setMargin(itemSizeHeader, new Insets(10, 30, 10, 30));
	    GridPane.setMargin(itemPriceHeader, new Insets(10, 30, 10, 30));

	    String[][] purchaseData = new String[50][5];
	    for (int i = 0; i < 50; i++) {
	        purchaseData[i][0] = "TR" + String.format("%04d", i + 1);
	        purchaseData[i][1] = "Item Name " + i;
	        purchaseData[i][2] = "Item Category";
	        purchaseData[i][3] = "Item Size";
	        purchaseData[i][4] = "Item Price";

	        Label transactionId = new Label(purchaseData[i][0]);
	        Label itemName = new Label(purchaseData[i][1]);
	        Label itemCategory = new Label(purchaseData[i][2]);
	        Label itemSize = new Label(purchaseData[i][3]);
	        Label itemPrice = new Label(purchaseData[i][4]);

	        historyTable.add(transactionId, 0, i + 1);
	        historyTable.add(itemName, 1, i + 1);
	        historyTable.add(itemCategory, 2, i + 1);
	        historyTable.add(itemSize, 3, i + 1);
	        historyTable.add(itemPrice, 4, i + 1);

	        GridPane.setMargin(transactionId, new Insets(10));
	        GridPane.setMargin(itemName, new Insets(10, 70, 10, 70));
	        GridPane.setMargin(itemCategory, new Insets(10, 30, 10, 30));
	        GridPane.setMargin(itemSize, new Insets(10, 30, 10, 30));
	        GridPane.setMargin(itemPrice, new Insets(10, 30, 10, 30));
	    }

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(historyTable);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPadding(new Insets(10));

	    purchaseHistoryLayout.setCenter(scrollPane);

	    return new Scene(purchaseHistoryLayout, 1000, 600);
	}





	private void addComponent(Stage stage) { 
	    // Header
	    HBox header = new HBox();
	    header.setPadding(new Insets(10));
	    header.setSpacing(10);
	    header.setBackground(new Background(new BackgroundFill(
	        Color.LIGHTGRAY, 
	        CornerRadii.EMPTY, 
	        Insets.EMPTY 
	    )));

	    MenuButton menuButton = new MenuButton("Menu");
	    MenuItem wishlistItem = new MenuItem("Wishlist");
	    MenuItem purchaseHistoryItem = new MenuItem("Purchase History");

	    wishlistItem.setOnAction(e -> stage.setScene(getWishlistScene(stage)));
	    purchaseHistoryItem.setOnAction(e -> stage.setScene(getPurchaseHistoryScene(stage)));

	    menuButton.getItems().addAll(wishlistItem, purchaseHistoryItem);

	    header.getChildren().add(menuButton);

	    // Search Bar
	    HBox searchBar = new HBox();
	    searchBar.setSpacing(10);
	    searchBar.setAlignment(Pos.CENTER);
	    searchBar.setPadding(new Insets(10, 0, 10, 0));
	    searchBar.setBackground(new Background(new BackgroundFill(
	        Color.WHITESMOKE, 
	        CornerRadii.EMPTY, 
	        Insets.EMPTY 
	    )));
	    TextField searchField = new TextField();
	    searchField.setPromptText("Search items...");
	    searchField.setPrefWidth(600);
	    Button searchButton = new Button("Search");

	    searchButton.setOnAction(e -> {
	        String query = searchField.getText();
	        if (!query.isEmpty()) {
	            System.out.println("Searching for: " + query);
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Search");
	            alert.setHeaderText(null);
	            alert.setContentText("Search results for: " + query);
	            alert.showAndWait();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Search Warning");
	            alert.setHeaderText(null);
	            alert.setContentText("Search field is empty. Please enter a keyword.");
	            alert.showAndWait();
	        }
	    });

	    searchBar.getChildren().addAll(searchField, searchButton);

	    VBox topContainer = new VBox();
	    topContainer.getChildren().addAll(header, searchBar);
	    topContainer.setSpacing(5);
	    BorderPane.setMargin(topContainer, new Insets(10));

	    borderContainer.setTop(topContainer);

	    gridContainer.setBackground(new Background(new BackgroundFill(
	        Color.LIGHTGRAY,
	        CornerRadii.EMPTY,
	        null
	    )));
	    gridContainer.setPadding(new Insets(10));
	    gridContainer.setHgap(10);
	    gridContainer.setVgap(10);

	    for (int i = 1; i <= 50; i++) {
	        Label rowId = new Label(String.valueOf(i));
	        Label rowItemName = new Label("Item Name " + i);
	        Label rowItemCategory = new Label("Item Category");
	        Label rowItemPrice = new Label("Item Price");
	        HBox rowActionButtons = new HBox(10);

	        Button rowPurchaseButton = new Button("Purchase");
	        Button rowOfferButton = new Button("Offer");
	        Button rowWishlistButton = new Button("Add to Wishlist");

	        rowActionButtons.getChildren().addAll(rowPurchaseButton, rowOfferButton, rowWishlistButton);

	        gridContainer.add(rowId, 0, i);
	        gridContainer.add(rowItemName, 1, i);
	        gridContainer.add(rowItemCategory, 2, i);
	        gridContainer.add(rowItemPrice, 3, i);
	        gridContainer.add(rowActionButtons, 4, i);

	        GridPane.setMargin(rowId, new Insets(10));
	        GridPane.setMargin(rowItemName, new Insets(10));
	        GridPane.setMargin(rowItemCategory, new Insets(10));
	        GridPane.setMargin(rowItemPrice, new Insets(10));
	        GridPane.setMargin(rowActionButtons, new Insets(10));
	    }

	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setContent(gridContainer);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPadding(new Insets(10));

	    borderContainer.setCenter(scrollPane);
	    BorderPane.setMargin(topContainer, Insets.EMPTY);

	}



	
	@Override
	public void start(Stage primaryStage) {
	    initialize();
	    addComponent(primaryStage);
	    primaryStage.setTitle("Homepage (Buyer)");
	    primaryStage.setScene(Homepage_Buyer);
	    primaryStage.show();
	}


	public static void main(String[] args) {
        launch(args);
    }
}