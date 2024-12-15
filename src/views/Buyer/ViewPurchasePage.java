package views.Buyer;

import controllers.TransactionController;
import models.Transaction;
import models.Wishlist;
import models.Item;
import services.Response;
import views.PageManager;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ViewPurchasePage implements EventHandler<ActionEvent> {

    private Scene scene;
    private BorderPane borderPane;
    private TableView<Transaction> transactionTable;
    private PageManager pageManager;

    public ViewPurchasePage(PageManager pageManager) {
        this.pageManager = pageManager;
        initUI();
        setLayout();
        loadHistory();
    }

    private void initUI() {
        borderPane = new BorderPane();

        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(10);
        Label title = new Label("Purchase History");
        title.setStyle("-fx-font-size:20px;");
        header.getChildren().add(title);

        // Menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homepageMenuItem = new MenuItem("Homepage");
        MenuItem wishlistMenuItem = new MenuItem("Wishlist");
        menu.getItems().addAll(homepageMenuItem, wishlistMenuItem);
        menuBar.getMenus().add(menu);

        homepageMenuItem.setOnAction(e -> pageManager.showBuyerDashboard());
        wishlistMenuItem.setOnAction(e -> pageManager.showViewWishlist());

        VBox topBox = new VBox(menuBar, header);
        borderPane.setTop(topBox);

        transactionTable = new TableView<>();
       
        
        TableColumn<Transaction, String> idColumn = new TableColumn<>("Transaction ID");
        idColumn.setCellValueFactory(param -> {
            String id = param.getValue().getTransaction_id(); 
            return new ReadOnlyObjectWrapper<>(id); 
        });
        
        TableColumn<Transaction, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(param -> {
            String itemName = param.getValue().product().item().getItem_name(); 
            return new ReadOnlyObjectWrapper<>(itemName); 
        });
        
        TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(param -> {
            String category = param.getValue().product().item().getItem_category(); 
            return new ReadOnlyObjectWrapper<>(category); 
        });
        
        TableColumn<Transaction, String> sizeColumn = new TableColumn<>("Item Size");
        sizeColumn.setCellValueFactory(param -> {
            String size = param.getValue().product().item().getItem_size();
            return new ReadOnlyObjectWrapper<>(size);
        });

        TableColumn<Transaction, BigDecimal> priceColumn = new TableColumn<>("Item Price");
        priceColumn.setCellValueFactory(param -> {
            BigDecimal price = param.getValue().product().item().getItem_price();
            return new ReadOnlyObjectWrapper<>(price);
        });
        
        transactionTable.getColumns().addAll(idColumn, itemNameColumn, categoryColumn, sizeColumn, priceColumn);

        ScrollPane scrollPane = new ScrollPane(transactionTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        borderPane.setCenter(transactionTable);

        scene = new Scene(borderPane, 1000, 600);
    }

    private void setLayout() {
        // Layout done in initUI
    }

    private void loadHistory() {
        String userId = pageManager.getLoggedInUser().getUser_id();
        Response<ArrayList<Transaction>> res = TransactionController.ViewHistory(userId);
        if (res.getIsSuccess()) {
            transactionTable.setItems(javafx.collections.FXCollections.observableArrayList(res.getData()));
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", res.getMessages());
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
