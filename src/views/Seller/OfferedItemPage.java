package views.Seller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Item;
import views.PageManager;

import java.util.Optional;

public class OfferedItemPage implements EventHandler<ActionEvent> {

    private BorderPane borderPane, headerSection;
    private TableView<Item> offeredItemsTable;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem dashboardMenuItem;
    private Label headerLabel;
    private Scene scene;
    private Stage primaryStage;
    
    private PageManager pageManager;

    public OfferedItemPage(PageManager pageManager) {
        this.pageManager = pageManager;
        this.primaryStage = pageManager.getPrimaryStage();
        initUI();
        initTable();
        setLayout();
        setMenuEventHandler();
    }

    private void initUI() {
        // Root container
        borderPane = new BorderPane();

        // Menu Bar
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        dashboardMenuItem = new MenuItem("Dashboard");

        menu.getItems().add(dashboardMenuItem);
        menuBar.getMenus().add(menu);

        // Header Label
        headerLabel = new Label("Offered Items");
        //headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Table
        offeredItemsTable = new TableView<>();
    }

    private void initTable() {
        // Define columns
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemIdColumn.setMinWidth(100);

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameColumn.setMinWidth(150);

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        itemCategoryColumn.setMinWidth(150);

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        itemSizeColumn.setMinWidth(100);

        TableColumn<Item, Integer> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemPriceColumn.setMinWidth(120);

        TableColumn<Item, Integer> offeredPriceColumn = new TableColumn<>("Offered Price");
        offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offeredPrice"));
        offeredPriceColumn.setMinWidth(120);

        TableColumn<Item, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setMinWidth(150);
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button acceptButton = new Button("ACCEPT");
            private final Button declineButton = new Button("DECLINE");

            {
                acceptButton.setOnAction(event -> {
                    Item offeredItem = getTableView().getItems().get(getIndex());
                    handleAccept(offeredItem);
                });

                declineButton.setOnAction(event -> {
                    Item offeredItem = getTableView().getItems().get(getIndex());
                    handleDecline(offeredItem);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(acceptButton, declineButton);
                    container.setSpacing(10);
                    setGraphic(container);
                }
            }
        });

        // Add columns to table
        offeredItemsTable.getColumns().addAll(
                itemIdColumn, itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn, offeredPriceColumn, actionColumn
        );
    }

    private void setLayout() {
        // Header Section
        headerSection = new BorderPane();
        headerSection.setTop(menuBar);
        headerSection.setCenter(headerLabel);

        // Layout
        borderPane.setTop(headerSection);
        borderPane.setCenter(offeredItemsTable);

        // Create Scene
        scene = new Scene(borderPane, 1000, 600);
    }

    private void handleAccept(Item item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Accept Item");
        alert.setContentText("Are you sure you want to accept this offer?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Handle accept logic
            System.out.println("Offer accepted for item: " + item);
        }
    }

    private void handleDecline(Item item) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Decline Offer");
        dialog.setHeaderText("Decline Item");
        dialog.setContentText("Reason:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String reason = result.get();
            // Handle decline logic
            //System.out.println("Offer declined for item: " + item.getItemName() + ". Reason: " + reason);
        }
    }

    private void setMenuEventHandler() {
        dashboardMenuItem.setOnAction(event -> {
        	pageManager.showSellerDashboard();
        });
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle any additional events if needed
    }
}
