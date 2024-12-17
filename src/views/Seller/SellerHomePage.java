package views.Seller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import models.Item;
import models.User;
import services.Response;
import views.PageManager;
import controllers.ItemController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class SellerHomePage implements EventHandler<ActionEvent> {

    private BorderPane borderPane, headerSection;
    private GridPane formPane;
    private TableView<Item> table;
    private TextField priceField; 
    private TextField itemNameField, itemCategoryField, itemSizeField;
    private Label header, itemNameLabel, itemCategoryLabel, itemSizeLabel, itemPriceLabel;
    private HBox buttonContainer;
    private Button submitButton;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem offeredItemsMenuItem;
    private Scene scene;

    private String temp_id;
    private PageManager pageManager;

    public SellerHomePage(PageManager pageManager) {
        this.pageManager = pageManager;
        initUI();
        initTable();
        initMenu();
        setLayout();
        setEventHandler();
        loadSellerItems();
    }

    private void initUI() {
        borderPane = new BorderPane();
        headerSection = new BorderPane();
        formPane = new GridPane();

        // Menu Bar consist only offered items.
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        offeredItemsMenuItem = new MenuItem("Offered Items");

        menu.getItems().add(offeredItemsMenuItem);
        menuBar.getMenus().add(menu);

        // Labels
        header = new Label("SELLER DASHBOARD");
        header.setStyle("-fx-font-size: 20px; -fx-padding: 10px;");
        itemNameLabel = new Label("Item Name");
        itemCategoryLabel = new Label("Item Category");
        itemSizeLabel = new Label("Item Size");
        itemPriceLabel = new Label("Item Price");

        // Input Fields
        itemNameField = new TextField();
        itemNameField.setPromptText("Enter item name");
        itemCategoryField = new TextField();
        itemCategoryField.setPromptText("Enter item category");
        itemSizeField = new TextField();
        itemSizeField.setPromptText("Enter item size");
        priceField = new TextField();
        priceField.setPromptText("Enter item price");

        // Buttons
        buttonContainer = new HBox(10);
        submitButton = new Button("Save");
        buttonContainer.getChildren().addAll(submitButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        // Table
        table = new TableView<>();
    }

   
    private void initTable() {
        // Define columns
        TableColumn<Item, String> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        itemIDColumn.setMinWidth(150);

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        itemNameColumn.setMinWidth(200);

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
        itemCategoryColumn.setMinWidth(150);

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
        itemSizeColumn.setMinWidth(100);

        TableColumn<Item, BigDecimal> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        itemPriceColumn.setMinWidth(100);

        // Action Buttons Column
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(150);
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(editButton, deleteButton);

            {
                pane.setSpacing(10);
                pane.setAlignment(Pos.CENTER);

                // Edit and Delete buttons for each row
                editButton.setOnAction(event -> {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    handleEdit(selectedItem);
                });

                deleteButton.setOnAction(event -> {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    handleDelete(selectedItem);
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

        // Add columns to table
        table.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn, actionColumn);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Listener for table row selection to store temp_id
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                temp_id = newSelection.getItem_id();
            }
        });
    }

    private void initMenu() {
        offeredItemsMenuItem.setOnAction(event -> pageManager.showOfferedItemPage());
    }


    private void setLayout() {
        // Layout for input form
        formPane.add(itemNameLabel, 0, 0);
        formPane.add(itemNameField, 1, 0);

        formPane.add(itemCategoryLabel, 0, 1);
        formPane.add(itemCategoryField, 1, 1);

        formPane.add(itemSizeLabel, 0, 2);
        formPane.add(itemSizeField, 1, 2);

        formPane.add(itemPriceLabel, 0, 3);
        formPane.add(priceField, 1, 3);

        formPane.add(buttonContainer, 1, 4);
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        // Header Section
        headerSection.setTop(menuBar);
        headerSection.setCenter(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        // Assemble BorderPane
        VBox topBox = new VBox(menuBar, header);
        borderPane.setTop(topBox);
        borderPane.setLeft(formPane);
        borderPane.setCenter(table);

        // Create Scene
        scene = new Scene(borderPane, 1000, 600);
    }

  
    private void setEventHandler() {
        submitButton.setOnAction(this);
       
    }

   
    private void loadSellerItems() {
    	
    	// Fetch seller items from db
        User currentUser = pageManager.getLoggedInUser();
  
        String sellerId = currentUser.getUser_id();
        ArrayList<Item> res = ItemController.ViewSellerItem(sellerId);
        ObservableList<Item> items = FXCollections.observableArrayList(res);
        
        table.setItems(FXCollections.observableArrayList(items));
        
    }
  
    @Override
    public void handle(ActionEvent event) {
    	
    	// Upload Item 
        if (event.getSource() == submitButton) {
        	
        	// Gather neccessary value
            String itemName = itemNameField.getText().trim();
            String itemCategory = itemCategoryField.getText().trim();
            String itemSize = itemSizeField.getText().trim();
            String itemPrice = priceField.getText().trim();
            
            User currentUser = pageManager.getLoggedInUser();
            String sellerId = currentUser.getUser_id();


            Response<Item> res = ItemController.UploadItem(sellerId,itemName, itemCategory, itemSize, itemPrice);
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item added successfully.");
                // Clear form fields
                itemNameField.clear();
                itemCategoryField.clear();
                itemSizeField.clear();
                priceField.clear();
                // Refresh table
                loadSellerItems();
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to add item.");
            }
        }
    }

 
    private void handleEdit(Item item) {
    	
    	// Edit form pop up
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit Item Details");

  
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

    
        // Create edit form page 
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField itemNameEdit = new TextField();
        itemNameEdit.setText(item.getItem_name());
        TextField itemCategoryEdit = new TextField();
        itemCategoryEdit.setText(item.getItem_category());
        TextField itemSizeEdit = new TextField();
        itemSizeEdit.setText(item.getItem_size());
        TextField priceEditField = new TextField();
        priceEditField.setText(item.getItem_price().toString());

        // Layout the form 
        grid.add(new Label("Item Name:"), 0, 0);
        grid.add(itemNameEdit, 1, 0);
        grid.add(new Label("Item Category:"), 0, 1);
        grid.add(itemCategoryEdit, 1, 1);
        grid.add(new Label("Item Size:"), 0, 2);
        grid.add(itemSizeEdit, 1, 2);
        grid.add(new Label("Item Price:"), 0, 3);
        grid.add(priceEditField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> itemNameEdit.requestFocus());

     
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return saveButtonType;
            }
            return null;
        });



        Optional<ButtonType> result = dialog.showAndWait();

        // Execute updated values
        if (result.isPresent() && result.get() == saveButtonType) {
            String editedName = itemNameEdit.getText().trim();
            String editedCategory = itemCategoryEdit.getText().trim();
            String editedSize = itemSizeEdit.getText().trim();
            String editedPrice= priceEditField.getText().trim();


            Response<Item> res = ItemController.EditItem(item.getItem_id(), editedName, editedCategory, editedSize, editedPrice);
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item updated successfully.");
                loadSellerItems();
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to update item.");
            }
        }
    }

  
    private void handleDelete(Item item) {
    	
    	
    	// Delete confirmation pop up
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Item");
        confirmationAlert.setContentText("Are you sure you want to delete the item: " + item.getItem_name() + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        
        // Execute Deletion of item
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Response<Item> res = ItemController.DeleteItem(item.getItem_id());
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item deleted successfully.");
                loadSellerItems();
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to delete item.");
            }
        }
    }



    
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

  
    public Scene getScene() {
        return scene;
    }
}
