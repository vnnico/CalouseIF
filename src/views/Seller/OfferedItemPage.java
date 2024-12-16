package views.Seller;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
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
import controllers.ItemController;
import models.Item;
import models.Offer;
import models.User;
import services.Response;
import views.PageManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class OfferedItemPage implements EventHandler<ActionEvent> {

    private BorderPane borderPane, headerSection;
    private TableView<Offer> offeredItemsTable;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem dashboardMenuItem;
    private Label headerLabel;
    private Scene scene;
    
    private PageManager pageManager;

    public OfferedItemPage(PageManager pageManager) {
        this.pageManager = pageManager;
        initUI();
        initTable();
        setLayout();
        setMenuEventHandler();
        loadOfferedItems();
    }

    private void initUI() {
        // Root container
        borderPane = new BorderPane();

        // Menu Bar consist only Dashboard Page
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        dashboardMenuItem = new MenuItem("Dashboard");

        menu.getItems().add(dashboardMenuItem);
        menuBar.getMenus().add(menu);

        // Header Label
        headerLabel = new Label("Offered Items");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-padding: 10px;");

        // Table
        offeredItemsTable = new TableView<>();
    }

    private void initTable() {
        // Define columns
        TableColumn<Offer, String> offerIdColumn = new TableColumn<>("Offer ID");
        offerIdColumn.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        offerIdColumn.setMinWidth(150);

        TableColumn<Offer, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(param -> {
            String itemName = param.getValue().product().item().getItem_name(); 
            return new ReadOnlyObjectWrapper<>(itemName); 
        });

        TableColumn<Offer, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(param -> {
            String category = param.getValue().product().item().getItem_category();
            return new ReadOnlyObjectWrapper<>(category);
        });

        TableColumn<Offer, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(param -> {
            String size = param.getValue().product().item().getItem_size();
            return new ReadOnlyObjectWrapper<>(size);
        });

        TableColumn<Offer, BigDecimal> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(param -> {
            BigDecimal price = param.getValue().product().item().getItem_price();
            return new ReadOnlyObjectWrapper<>(price);
        });
        
        TableColumn<Offer, BigDecimal> offerPriceColumn = new TableColumn<>("Offered Price");
        offerPriceColumn.setCellValueFactory(param -> {
            BigDecimal offerPrice = param.getValue().getItem_offer_price();
            return new ReadOnlyObjectWrapper<>(offerPrice);
        });


        TableColumn<Offer, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setMinWidth(200);
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button declineButton = new Button("Decline");
            private final HBox pane = new HBox(10, acceptButton, declineButton);

            {
                pane.setAlignment(Pos.CENTER);

                // Accept and Decline button for each row 
                acceptButton.setOnAction(event -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    handleAccept(offer);
                });

                declineButton.setOnAction(event -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    handleDecline(offer);
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
        offeredItemsTable.getColumns().addAll(
                offerIdColumn, nameColumn, categoryColumn, sizeColumn, priceColumn, offerPriceColumn,actionColumn
        );
    }

    private void setLayout() {
        // Header Section
        headerSection = new BorderPane();
        headerSection.setTop(menuBar);
        headerSection.setCenter(headerLabel);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);

        // Assemble BorderPane
        borderPane.setTop(headerSection);
        borderPane.setCenter(offeredItemsTable);
        borderPane.setPadding(new Insets(10));
        
        // Create Scene
        scene = new Scene(borderPane, 1000, 600);
    }

    private void setMenuEventHandler() {
        dashboardMenuItem.setOnAction(event -> pageManager.showSellerDashboard());
    }

    private void loadOfferedItems() {
    	
    	// Fetch offered items from db
        User currentUser = pageManager.getLoggedInUser();
        
        
        String sellerId = currentUser.getUser_id();
        Response<ArrayList<Offer>> res = ItemController.ViewOfferItem(sellerId);
        if (res.getIsSuccess() && res.getData() != null) {
            offeredItemsTable.setItems(FXCollections.observableArrayList(res.getData()));
            System.out.println();
        } else {
            showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to load offered items.");
        }
    }

    private void handleAccept(Offer offer) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Acceptance");
        confirmationAlert.setHeaderText("Accept Offer");
        confirmationAlert.setContentText("Are you sure you want to accept this offer?");
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Call controller to accept the offer
            Response<Offer> res = ItemController.AcceptOffer(offer.getOffer_id());
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Offer accepted successfully.");
                loadOfferedItems(); // Refresh the table
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to accept offer.");
            }
        }
    }

    private void handleDecline(Offer offer) {
        // Create a popup input form to enter the reason for declining
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Decline Offer");
        dialog.setHeaderText("Enter Reason for Declining the Offer");

        // Set the button types
        ButtonType declineButtonType = new ButtonType("Decline", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(declineButtonType, ButtonType.CANCEL);

        // Create the reason input field
        TextArea reasonTextArea = new TextArea();
        reasonTextArea.setPromptText("Enter reason for declining the offer...");
        reasonTextArea.setWrapText(true);
        reasonTextArea.setPrefRowCount(4);

        VBox content = new VBox(new Label("Reason:"), reasonTextArea);
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(content);

        // Enable/Disable Decline button based on input
        Node declineButton = dialog.getDialogPane().lookupButton(declineButtonType);
        declineButton.setDisable(true);

        reasonTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            declineButton.setDisable(newValue.trim().isEmpty());
        });

        // Convert the result to the reason string when Decline button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == declineButtonType) {
                return reasonTextArea.getText().trim();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(reason -> {
            // Decline the offer with the provided reason
            Response<Offer> res = ItemController.DeclineOffer(offer.getOffer_id(), reason);
            if (res.getIsSuccess()) {
                showAlert(AlertType.INFORMATION, "Success", "Offer declined successfully.");
                loadOfferedItems(); // Refresh the table
            } else {
                showAlert(AlertType.ERROR, "Error", res.getMessages() != null ? res.getMessages() : "Failed to decline offer.");
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

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Scene getScene() {
		return scene;
	}
}
