package views.Admin;

import controllers.ItemController;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Item;
import services.Response;
import views.PageManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class AdminHomePage implements EventHandler<ActionEvent> {

    private BorderPane borderPane;
    private Label headerLabel;
    private TableView<Item> tableView;
    private Scene scene;
    private PageManager pageManager;

    public AdminHomePage(PageManager pageManager) {
        this.pageManager = pageManager;
        initializeUI();
        initializeTable();
        layoutComponents();
        loadRequestedItems();
    }

    private void initializeUI() {
        borderPane = new BorderPane();
        headerLabel = new Label("ADMIN DASHBOARD");
        headerLabel.setStyle("-fx-font-size: 20px; ");
 
    }


    private void initializeTable() {
        tableView = new TableView<>();
        tableView.setPrefWidth(980);
        tableView.setPlaceholder(new Label("No items awaiting approval"));

        TableColumn<Item, String> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        itemIDColumn.setMinWidth(100);

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        itemNameColumn.setMinWidth(150);

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
        itemCategoryColumn.setMinWidth(150);

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
        itemSizeColumn.setMinWidth(100);

        TableColumn<Item, BigDecimal> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        itemPriceColumn.setMinWidth(100);

        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("item_status"));
        itemStatusColumn.setMinWidth(120);

    
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(200);
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");
            private final HBox pane = new HBox(10, approveButton, declineButton);

            {
                pane.setAlignment(Pos.CENTER);

                approveButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleApprove(item);
                });

                declineButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    handleDecline(item);
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

   
        tableView.getColumns().addAll(
                itemIDColumn,
                itemNameColumn,
                itemCategoryColumn,
                itemSizeColumn,
                itemPriceColumn,
                itemStatusColumn,
                actionColumn
        );
    }

 
    private void layoutComponents() {
        // Header Section
        VBox headerBox = new VBox(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 10, 0));

        borderPane.setTop(headerBox);
        borderPane.setCenter(tableView);

     
        scene = new Scene(borderPane, 1000, 600);
    }


    private void loadRequestedItems() {
     
        Response<ArrayList<Item>> response = ItemController.ViewRequestItem("Pending");
        
        if (response.getIsSuccess() && response.getData() != null) {
            ObservableList<Item> items = FXCollections.observableArrayList(response.getData());
            tableView.setItems(items);
        } else {
            showAlert(AlertType.ERROR, "Error", response.getMessages() != null ? response.getMessages() : "Failed to load requested items.");
        }
    }


    private void handleApprove(Item item) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Approval");
        
        confirmationAlert.setHeaderText("Approve Item");
        
        confirmationAlert.setContentText("Are you sure you want to approve the item: " + item.getItem_name() + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	
            Response<Item> res = ItemController.ApproveItem(item.getItem_id());
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item approved successfully.");
                loadRequestedItems(); // Refresh the table
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to approve item.");
            }
        }
    }



    private void handleDecline(Item item) {


        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Decline Item");
        dialog.setHeaderText("Enter Reason for Declining the Item: " + item.getItem_name());



        ButtonType declineButtonType = new ButtonType("Decline", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(declineButtonType, ButtonType.CANCEL);



        TextArea reasonTextArea = new TextArea();
        
        reasonTextArea.setPromptText("Enter reason here...");
        reasonTextArea.setWrapText(true);
        reasonTextArea.setPrefRowCount(4);

        VBox content = new VBox(new Label("Reason:"), reasonTextArea);
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(content);



        Node declineButton = dialog.getDialogPane().lookupButton(declineButtonType);
        declineButton.setDisable(true);

        reasonTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            declineButton.setDisable(newValue.trim().isEmpty());
        });



        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == declineButtonType) {
                return reasonTextArea.getText().trim();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(reason -> {
            Response<Item> res = ItemController.DeclineItem(item.getItem_id(), reason);
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Item declined successfully.");
                loadRequestedItems();

            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to decline item.");
            }
        });
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

    @Override
    public void handle(ActionEvent event) {
           }
}
