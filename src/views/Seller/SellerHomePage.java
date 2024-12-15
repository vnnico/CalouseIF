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
    private TextField priceField; // Replaced Spinner with TextField
    private TextField itemNameField, itemCategoryField, itemSizeField;
    private Label header, itemNameLabel, itemCategoryLabel, itemSizeLabel, itemPriceLabel;
    private HBox buttonContainer;
    private Button submitButton;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem offeredItemsMenuItem;
    private Scene scene;

    private String temp_id; // To store the item_id of the selected item

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

    /**
     * Initialize UI components excluding the table and menu.
     */
    private void initUI() {
        borderPane = new BorderPane();
        headerSection = new BorderPane();
        formPane = new GridPane();

        // Menu
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

    /**
     * Initialize the table with necessary columns and action buttons.
     */
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

        // Listener for table row selection to store temp_id (optional if needed)
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                temp_id = newSelection.getItem_id();
            }
        });
    }

    /**
     * Initialize the menu with event handlers.
     */
    private void initMenu() {
        offeredItemsMenuItem.setOnAction(event -> pageManager.showOfferedItemPage());
    }

    /**
     * Set up the layout of the page, arranging all components appropriately.
     */
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

    /**
     * Set event handlers for buttons and other interactive components.
     */
    private void setEventHandler() {
        submitButton.setOnAction(this);
        // Edit and Delete buttons are handled within the table's action column
    }

    /**
     * Load the seller's items from the backend and populate the table.
     */
    private void loadSellerItems() {
        User currentUser = pageManager.getLoggedInUser();
        if (currentUser == null) {
            showAlert(AlertType.ERROR, "User Not Logged In", "Please log in to view your items.");
            pageManager.showLoginPage();
            return;
        }
        String sellerId = currentUser.getUser_id();
        ArrayList<Item> res = ItemController.ViewSellerItem(sellerId);
        ObservableList<Item> items = FXCollections.observableArrayList(res);
        
        table.setItems(FXCollections.observableArrayList(items));
        
    }

    /**
     * Handle the action when the submit button is clicked to upload a new item.
     */
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == submitButton) {
            String itemName = itemNameField.getText().trim();
            String itemCategory = itemCategoryField.getText().trim();
            String itemSize = itemSizeField.getText().trim();
            String itemPrice = priceField.getText().trim();
            
            User currentUser = pageManager.getLoggedInUser();
            String sellerId = currentUser.getUser_id();


            // Call controller to upload item with individual parameters
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

    /**
     * Handle the action when the edit button is clicked to modify an existing item.
     */
    private void handleEdit(Item item) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit Item Details");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form fields
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

        grid.add(new Label("Item Name:"), 0, 0);
        grid.add(itemNameEdit, 1, 0);
        grid.add(new Label("Item Category:"), 0, 1);
        grid.add(itemCategoryEdit, 1, 1);
        grid.add(new Label("Item Size:"), 0, 2);
        grid.add(itemSizeEdit, 1, 2);
        grid.add(new Label("Item Price:"), 0, 3);
        grid.add(priceEditField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the first field by default
        Platform.runLater(() -> itemNameEdit.requestFocus());

        // Enable/Disable Save button based on input validation
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Convert the result to the edited values when the Save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String editedName = itemNameEdit.getText().trim();
                String editedCategory = itemCategoryEdit.getText().trim();
                String editedSize = itemSizeEdit.getText().trim();
                String editedPriceStr = priceEditField.getText().trim();

                // Validate price
                BigDecimal editedPrice;
                try {
                    editedPrice = new BigDecimal(editedPriceStr);
                    if (editedPrice.compareTo(BigDecimal.ZERO) <= 0) {
                        showAlert(AlertType.WARNING, "Input Validation", "Item price must be greater than zero.");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number for the item price.");
                    return null;
                }

                // Return the save action with edited details
                return new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            }
            return null;
        });

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == saveButtonType) {
            String editedName = itemNameEdit.getText().trim();
            String editedCategory = itemCategoryEdit.getText().trim();
            String editedSize = itemSizeEdit.getText().trim();
            String editedPrice= priceEditField.getText().trim();

          
            // Call controller to update item with individual parameters
            Response<Item> res = ItemController.EditItem(item.getItem_id(), editedName, editedCategory, editedSize, editedPrice);
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item updated successfully.");
                loadSellerItems();
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to update item.");
            }
        }
    }

    /**
     * Handle the action when the delete button is clicked to remove an existing item.
     */
    private void handleDelete(Item item) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Item");
        confirmationAlert.setContentText("Are you sure you want to delete the item: " + item.getItem_name() + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
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

    /**
     * Validate the edit form fields to enable or disable the Save button.
     */
//    private void validateEditForm(TextField nameField, TextField categoryField, TextField sizeField, TextField priceField, Node saveButton) {
//        String name = nameField.getText().trim();
//        String category = categoryField.getText().trim();
//        String size = sizeField.getText().trim();
//        String priceStr = priceField.getText().trim();
//
//        boolean disable = name.isEmpty() || category.isEmpty() || size.isEmpty() || priceStr.isEmpty();
//
//        // Additionally, check if the price is a valid number
//        if (!priceStr.isEmpty()) {
//            try {
//                BigDecimal price = new BigDecimal(priceStr);
//                if (price.compareTo(BigDecimal.ZERO) <= 0) {
//                    disable = true;
//                }
//            } catch (NumberFormatException e) {
//                disable = true;
//            }
//        }
//
//        saveButton.setDisable(disable);
//    }

    /**
     * Display an alert dialog to the user.
     */
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Getter for the scene.
     */
    public Scene getScene() {
        return scene;
    }
}
