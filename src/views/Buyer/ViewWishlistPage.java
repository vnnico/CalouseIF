package views.Buyer;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import views.PageManager;
import javafx.scene.paint.Color;

import java.util.Optional;

public class ViewWishlistPage {

    private Scene scene;
    private BorderPane borderPane;
    private GridPane wishlistTable;
    
    private HBox header;
    private MenuButton menuButton;
    private MenuItem homepageMenuItem, purchaseHistoryMenuItem;
    
    private Stage primaryStage;

    private PageManager pageManager;
    
    public ViewWishlistPage(PageManager pageManager) {
    	 this.pageManager = pageManager;
         this.primaryStage = pageManager.getPrimaryStage();
        initUI();
        initMenu();
        setLayout();
    }

    private void initUI() {
        borderPane = new BorderPane();
        wishlistTable = new GridPane();

        // Header
        header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(10);
        header.setBackground(new Background(new BackgroundFill(
            Color.LIGHTGRAY, 
            CornerRadii.EMPTY, 
            Insets.EMPTY 
        )));
        
        menuButton = new MenuButton("Menu");
        homepageMenuItem = new MenuItem("Homepage");
        purchaseHistoryMenuItem = new MenuItem("Purchase History");

        borderPane.setTop(header);
    }

    private void setLayout() {
        // Setup Wishlist Table Headers
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

        // Contoh Data Wishlist
        for (int i = 1; i <= 20; i++) {
            Label rowId = new Label(String.valueOf(i));
            Label rowItemName = new Label("Item Name " + i);
            Label rowItemCategory = new Label("Category " + i);
            Label rowItemSize = new Label("Size " + i);
            Label rowItemPrice = new Label("$" + (10 * i));
            Button removeButton = new Button("Remove");

            removeButton.setOnAction(e -> handleRemove(rowItemName.getText()));

            GridPane.setHalignment(removeButton, HPos.CENTER);

            wishlistTable.add(rowId, 0, i);
            wishlistTable.add(rowItemName, 1, i);
            wishlistTable.add(rowItemCategory, 2, i);
            wishlistTable.add(rowItemSize, 3, i);
            wishlistTable.add(rowItemPrice, 4, i);
            wishlistTable.add(removeButton, 5, i);

            GridPane.setMargin(rowId, new Insets(10));
            GridPane.setMargin(rowItemName, new Insets(10, 10, 10, 10));
            GridPane.setMargin(rowItemCategory, new Insets(10, 10, 10, 10));
            GridPane.setMargin(rowItemSize, new Insets(10, 10, 10, 10));
            GridPane.setMargin(rowItemPrice, new Insets(10, 10, 10, 10));
            GridPane.setMargin(removeButton, new Insets(10, 10, 10, 10));
        }

        ScrollPane scrollPane = new ScrollPane(wishlistTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        borderPane.setCenter(scrollPane);
        scene = new Scene(borderPane, 1000, 600);
    }

    private void initMenu() {
    	menuButton.getItems().addAll(homepageMenuItem, purchaseHistoryMenuItem);
        header.getChildren().add(menuButton);
        
        homepageMenuItem.setOnAction(event -> {
        	pageManager.showBuyerDashboard();
        });
        
        purchaseHistoryMenuItem.setOnAction(event -> {
        	pageManager.showViewPurchaseHistory();
        });
        
        
    }
    private void handleRemove(String itemName) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Remove Item");
        confirmationAlert.setContentText("Are you sure you want to remove " + itemName + " from the wishlist?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Removed from wishlist: " + itemName);
            // Tambahkan logika penghapusan item dari wishlist di sini
        }
    }

    public Scene getScene() {
        return scene;
    }
}
