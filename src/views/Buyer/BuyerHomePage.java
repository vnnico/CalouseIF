package views.Buyer;

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
import views.PageManager;

import java.util.Optional;

public class BuyerHomePage implements EventHandler<ActionEvent> {

    private BorderPane borderPane;
    private VBox headerContainer;
    private TableView<Item> tableView;

    private Label header;
    private TextField searchField;
    private Button searchButton;

    private MenuBar menuBar;
    private Menu menu;
    private MenuItem wishlistMenuItem, purchaseHistoryMenuItem;

    private Scene scene;
    private Stage primaryStage;

    private ViewPurchasePage viewPurchasePage;
    private ViewWishlistPage viewWishlistPage;

    // ObservableList untuk menyimpan data item
    private ObservableList<Item> itemList;
    
    private PageManager pageManager;
   

    public BuyerHomePage(PageManager pageManager) {
    	this.pageManager = pageManager;
        this.primaryStage = pageManager.getPrimaryStage();

        initUI();
        initMenu();
        setLayout();
     
    }

    private void initUI() {
        borderPane = new BorderPane();

        // Header
        header = new Label("BUYER DASHBOARD");
        header.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

        // Menu
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        wishlistMenuItem = new MenuItem("Wishlist");
        purchaseHistoryMenuItem = new MenuItem("Purchase History");

        // Search Bar
        searchField = new TextField();
        searchField.setPromptText("Search items...");
        searchField.setPrefWidth(400);
        searchButton = new Button("Search");

        HBox searchBar = new HBox(10, searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(10));

        // Combine Header and Search Bar
        headerContainer = new VBox(menuBar, header, searchBar);
        headerContainer.setSpacing(5);

        borderPane.setTop(headerContainer);
    }

    private void setLayout() {
        // Inisialisasi TableView
        tableView = new TableView<>();
        tableView.setPrefWidth(980);
        tableView.setPlaceholder(new Label("No items available"));

        // Define columns
        TableColumn<Item, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setMinWidth(150);
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setMinWidth(100);
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(200);
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button purchaseButton = new Button("Purchase");
            private final Button addToWishlistButton = new Button("Add to Wishlist");
            private final HBox pane = new HBox(purchaseButton, addToWishlistButton);

            {
                pane.setSpacing(10);
                purchaseButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    //handlePurchase(item.getName());
                });
                addToWishlistButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    //handleAddToWishlist(item.getName());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });

        // Tambahkan kolom ke TableView
        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, actionColumn);

        // Inisialisasi data
        itemList = FXCollections.observableArrayList();
        populateItems(); // Method untuk mengisi data awal

        tableView.setItems(itemList);

        // ScrollPane tidak diperlukan karena TableView sudah dapat discroll secara internal
        borderPane.setCenter(tableView);

        scene = new Scene(borderPane, 1000, 600);
    }

    private void initMenu() {
    
        menu.getItems().addAll(wishlistMenuItem, purchaseHistoryMenuItem);
        menuBar.getMenus().add(menu);
        
        wishlistMenuItem.setOnAction(event -> {
        	pageManager.showViewWishlist();
        });
        
        purchaseHistoryMenuItem.setOnAction(event -> {
        	pageManager.showViewPurchaseHistory();
        });
        
    }


    private void populateItems() {
        // Contoh data, seharusnya diambil dari database atau sumber data lain
        for (int i = 1; i <= 20; i++) {
//            itemList.add(new Item(
//                    String.valueOf(i),
//                    "Item Name " + i,
//                    "Category " + ((i % 5) + 1),
//                    "Size " + ((i % 3) + 1),
//                    10.0 * i
//            ));
        }
    }

    private void handleSearch() {
//        String query = searchField.getText().toLowerCase();
//        if (!query.isEmpty()) {
//            // Filter itemList berdasarkan query
//            ObservableList<Item> filteredList = FXCollections.observableArrayList();
//            for (Item item : itemList) {
////                if (item.getName().toLowerCase().contains(query) ||
////                    item.getCategory().toLowerCase().contains(query)) {
////                    filteredList.add(item);
//                }
//            }
//            //tableView.setItems(filteredList);
//
//            if (filteredList.isEmpty()) {
//                Alert alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("Search Results");
//                alert.setHeaderText(null);
//                alert.setContentText("No items found for: " + query);
//                alert.showAndWait();
//            }
//        } else {
//            tableView.setItems(itemList); // Reset ke seluruh item
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.setTitle("Search Warning");
//            alert.setHeaderText(null);
//            alert.setContentText("Search field is empty. Please enter a keyword.");
//            alert.showAndWait();
//        }
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void handle(ActionEvent event) {
        // Tidak digunakan karena aksi sudah di-handle di TableCell
    }

    private void handlePurchase(String itemName) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Purchase Confirmation");
        confirmationAlert.setContentText("Are you sure you want to purchase " + itemName + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Purchase confirmed for: " + itemName);
            // Tambahkan logika pembelian di sini
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("You have successfully purchased: " + itemName);
            successAlert.showAndWait();
        } else {
            System.out.println("Purchase canceled for: " + itemName);
        }
    }

    private void handleAddToWishlist(String itemName) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Add to Wishlist");
        confirmationAlert.setContentText("Are you sure you want to add " + itemName + " to your wishlist?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Item added to wishlist: " + itemName);
            // Tambahkan logika penambahan ke wishlist di sini
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Wishlist Updated");
            successAlert.setHeaderText(null);
            successAlert.setContentText(itemName + " has been added to your wishlist.");
            successAlert.showAndWait();
        } else {
            System.out.println("Adding to wishlist canceled for: " + itemName);
        }
    }
}
