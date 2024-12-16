package views.Buyer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Product;
import models.Wishlist;
import services.Response;
import views.PageManager;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import controllers.WishlistController;

public class ViewWishlistPage implements EventHandler<ActionEvent>{


    private Scene scene;
    private BorderPane borderPane;
    private TableView<Wishlist> wishlistTable;
    private PageManager pageManager;

    public ViewWishlistPage(PageManager pageManager) {
        this.pageManager = pageManager;
        initUI();
        setLayout();
        loadWishlist();
    }

    private void initUI() {
        borderPane = new BorderPane();
        
        
        // Header and title section
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Your Wishlist");
        title.setStyle("-fx-font-size:20px;");
        header.getChildren().add(title);

        // Menu bar consist of Home page and Purchase History pages
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homepageMenuItem = new MenuItem("Homepage");
        MenuItem purchaseHistoryMenuItem = new MenuItem("Purchase History");
        menu.getItems().addAll(homepageMenuItem, purchaseHistoryMenuItem);
        menuBar.getMenus().add(menu);

        // When menu item clicked, it will redirect to desired page
        homepageMenuItem.setOnAction(e -> pageManager.showBuyerDashboard());
        purchaseHistoryMenuItem.setOnAction(e -> pageManager.showViewPurchaseHistory());

        VBox topBox = new VBox(menuBar, header);
        borderPane.setTop(topBox);

        wishlistTable = new TableView<>();

        
        // Configure Tables
        TableColumn<Wishlist, String> idColumn = new TableColumn<>("Wishlist ID");
        idColumn.setCellValueFactory(param -> {
            String id = param.getValue().getWishlist_id(); 
            return new ReadOnlyObjectWrapper<>(id); 
        });
        


        TableColumn<Wishlist, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(param -> {
            String itemName = param.getValue().product().item().getItem_name(); 
            return new ReadOnlyObjectWrapper<>(itemName); 
        });
        
        TableColumn<Wishlist, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(param -> {
            String category = param.getValue().product().item().getItem_category(); 
            return new ReadOnlyObjectWrapper<>(category); 
        });
        
        TableColumn<Wishlist, String> sizeColumn = new TableColumn<>("Item Size");
        sizeColumn.setCellValueFactory(param -> {
            String size = param.getValue().product().item().getItem_size();
            return new ReadOnlyObjectWrapper<>(size);
        });

        TableColumn<Wishlist, BigDecimal> priceColumn = new TableColumn<>("Item Price");
        priceColumn.setCellValueFactory(param -> {
            BigDecimal price = param.getValue().product().item().getItem_price();
            return new ReadOnlyObjectWrapper<>(price);
        });

     
        TableColumn<Wishlist, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
            	
            	// Remove Item
                removeButton.setOnAction(e -> {
                    Wishlist w = getTableView().getItems().get(getIndex());
                    handleRemove(w);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
        
        
        // Set column width
        idColumn.setMinWidth(100);
        itemNameColumn.setMinWidth(150);
        categoryColumn.setMinWidth(150);
        sizeColumn.setMinWidth(100);
        priceColumn.setMinWidth(100);
        
        wishlistTable.getColumns().addAll(idColumn, itemNameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);

        // Scrollbar
        ScrollPane scrollPane = new ScrollPane(wishlistTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        borderPane.setCenter(wishlistTable);

        scene = new Scene(borderPane, 1000, 600);
    }

    private void setLayout() {
        
    }

    private void loadWishlist() {
        String userId = pageManager.getLoggedInUser().getUser_id();
        Response<ArrayList<Wishlist>> res = WishlistController.ViewWishlist(userId);
        if (res.getIsSuccess()) {
            wishlistTable.setItems(javafx.collections.FXCollections.observableArrayList(res.getData()));
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", res.getMessages());
        }
    }

    private void handleRemove(Wishlist w) {
    	
    	// Remove Confirmation Popup
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Remove Item");
        confirmationAlert.setContentText("Are you sure you want to remove this item from the wishlist?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Response<Wishlist> res = WishlistController.RemoveWishlist(w.getWishlist_id());
            if (res.getIsSuccess()) {
                showAlert(Alert.AlertType.INFORMATION, "Wishlist Updated", "Item removed from wishlist.");
                wishlistTable.getItems().remove(w);
            } else {
                showAlert(Alert.AlertType.ERROR, "Removal Failed", res.getMessages());
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
    }

    public Scene getScene() {
        return scene;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
