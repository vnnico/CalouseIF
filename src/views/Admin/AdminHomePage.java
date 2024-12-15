package views.Admin;

import java.math.BigDecimal;

import controllers.ItemController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Item;
import views.PageManager;

public class AdminHomePage {

    private BorderPane borderPane;
    private TableView<Item> table;
    private Label header;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem menuItem;
    
    private Scene scene;
    private Stage primaryStage;
    private PageManager pageManager;

    public AdminHomePage(PageManager pageManager) {
    	this.pageManager = pageManager;
        this.primaryStage = pageManager.getPrimaryStage();
        initUI();
        initTable();
        setLayout();
    }

    private void initUI() {
        borderPane = new BorderPane();

        // Menu Bar
        menuBar = new MenuBar();
        menu = new Menu("Menu");
//        menuItem = new MenuItem("Offered Items");
//        menu.getItems().add(menuItem);
//        menuBar.getMenus().add(menu);

        // Header Label
        header = new Label("ADMIN DASHBOARD");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // TableView
        table = new TableView<>();

        // Scene
        scene = new Scene(borderPane, 1000, 600);
    }

    private void initTable() {
        TableColumn<Item, String> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemIDColumn.setMinWidth(150);

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameColumn.setMinWidth(150);

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        itemCategoryColumn.setMinWidth(150);

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        itemSizeColumn.setMinWidth(150);

        TableColumn<Item, BigDecimal> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemPriceColumn.setMinWidth(150);
        
        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));
        itemSizeColumn.setMinWidth(150);
        
        TableColumn<Item, Void> itemActionColumn = new TableColumn<>("Action");
        itemActionColumn.setMinWidth(200);
        addActionButtonsToColumn(itemActionColumn);

        table.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn, itemStatusColumn, itemActionColumn);
    }
    
    private void addActionButtonsToColumn(TableColumn<Item, Void> column) {
        ItemController controller = new ItemController();

        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");
            private final HBox buttonsBox = new HBox(10);

            {
                approveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                declineButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                buttonsBox.setAlignment(Pos.CENTER);
                buttonsBox.getChildren().addAll(approveButton, declineButton);

                approveButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        controller.approveItem(item.getItem_id()); // Approve in DB
                        System.out.println("Item approved: " + item.getItem_id());

                        //refresh table
                        getTableView().getItems().setAll(controller.getAllItems());
                    }
                });

                declineButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        controller.declineItem(item.getItem_id()); // Decline in DB
                        System.out.println("Item declined: " + item.getItem_id());

                        getTableView().getItems().setAll(controller.getAllItems());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonsBox);
                }
            }
        };

        column.setCellFactory(cellFactory);
    }


    private void setLayout() {
        StackPane headerPane = new StackPane();
        headerPane.getChildren().add(header);
        headerPane.setAlignment(Pos.CENTER);

        BorderPane topPane = new BorderPane();
        topPane.setTop(menuBar);
        topPane.setCenter(headerPane);

        borderPane.setTop(topPane);
        borderPane.setCenter(table);
    }

    public Scene getScene() {
        return scene;
    }
}
