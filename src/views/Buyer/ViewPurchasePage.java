package views.Buyer;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import views.PageManager;

public class ViewPurchasePage {

    private Scene scene;
    private BorderPane borderPane;
    private GridPane historyTable;
    private HBox header;
    private MenuButton menuButton;
    private MenuItem homepageMenuItem, wishlistMenuItem;
    private Stage primaryStage;

    private PageManager pageManager;
    
    public ViewPurchasePage(PageManager pageManager) {
    	 this.pageManager = pageManager;
         this.primaryStage = pageManager.getPrimaryStage();
        initUI();
        initMenu();
        setLayout();
    }

    private void initUI() {
        borderPane = new BorderPane();
        historyTable = new GridPane();

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
        wishlistMenuItem = new MenuItem("Wishlist");

        
        borderPane.setTop(header);
    }

    private void setLayout() {
        // Setup Purchase History Table Headers
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

        // Contoh Data Purchase History
        for (int i = 1; i <= 20; i++) {
            Label transactionId = new Label("TR" + String.format("%04d", i));
            Label itemName = new Label("Item Name " + i);
            Label itemCategory = new Label("Category " + i);
            Label itemSize = new Label("Size " + i);
            Label itemPrice = new Label("$" + (20 * i));

            historyTable.add(transactionId, 0, i);
            historyTable.add(itemName, 1, i);
            historyTable.add(itemCategory, 2, i);
            historyTable.add(itemSize, 3, i);
            historyTable.add(itemPrice, 4, i);

            GridPane.setMargin(transactionId, new Insets(10));
            GridPane.setMargin(itemName, new Insets(10, 10, 10, 10));
            GridPane.setMargin(itemCategory, new Insets(10, 10, 10, 10));
            GridPane.setMargin(itemSize, new Insets(10, 10, 10, 10));
            GridPane.setMargin(itemPrice, new Insets(10, 10, 10, 10));
        }

        ScrollPane scrollPane = new ScrollPane(historyTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        borderPane.setCenter(scrollPane);
        scene = new Scene(borderPane, 1000, 600);
    }

    private void initMenu() {

        menuButton.getItems().addAll(homepageMenuItem, wishlistMenuItem);
        header.getChildren().add(menuButton);
        
        
        homepageMenuItem.setOnAction(event -> {
        	pageManager.showBuyerDashboard();
        });
        
        wishlistMenuItem.setOnAction(event -> {
        	pageManager.showViewWishlist();
        });
    }
    
    public Scene getScene() {
        return scene;
    }
}
