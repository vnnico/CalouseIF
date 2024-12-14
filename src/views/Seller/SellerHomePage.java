package views.Seller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Item;

import java.math.BigDecimal;

public class SellerHomePage implements EventHandler<ActionEvent> {

    private BorderPane borderPane, headerSection;
    private GridPane formPane;
    private TableView<Item> table;
    private Spinner<Integer> priceSpinner;
    private TextField itemNameField, itemCategoryField, itemSizeField;
    private Label header, itemNameLabel, itemCategoryLabel, itemSizeLabel, itemPriceLabel;
    private HBox buttonContainer;
    private Button submitButton, editButton, deleteButton;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem menuItem;
    private Scene scene;
    
    private String temp_id;
    


    public SellerHomePage() {
        initUI();
        initTable();
        initMenu();
        setLayout();
        setEventHandler();
    }

    private void initUI() {
        borderPane = new BorderPane();
        headerSection = new BorderPane();
        formPane = new GridPane();

        // Menu
        menuBar = new MenuBar();
        menu = new Menu("Menu");
        menuItem = new MenuItem("Offered Items");

        // Labels
        header = new Label("SELLER DASHBOARD");
        itemNameLabel = new Label("Item Name");
        itemCategoryLabel = new Label("Item Category");
        itemSizeLabel = new Label("Item Size");
        itemPriceLabel = new Label("Item Price");

        // Input Fields
        itemNameField = new TextField();
        itemCategoryField = new TextField();
        itemSizeField = new TextField();
        priceSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        priceSpinner.setEditable(true);

        // Button
        buttonContainer = new HBox();
        submitButton = new Button("Save");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");

        // Table
        table = new TableView<>();

        // Scene
        scene = new Scene(borderPane, 1000, 600);
    }

    private void initTable() {
        TableColumn<Item, String> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemIDColumn.setMinWidth(200);

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemNameColumn.setMinWidth(200);

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        itemCategoryColumn.setMinWidth(200);

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        itemSizeColumn.setMinWidth(200);

        TableColumn<Item, Integer> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemPriceColumn.setMinWidth(200);

        table.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn);
        table.setOnMouseClicked(setMouseEvent());
    }

    private void initMenu() {
        menuBar.getMenus().add(menu);
        menu.getItems().add(menuItem);
    }

    private void setLayout() {
        formPane.add(itemNameLabel, 0, 1);
        formPane.add(itemNameField, 1, 1);

        formPane.add(itemCategoryLabel, 0, 2);
        formPane.add(itemCategoryField, 1, 2);

        formPane.add(itemSizeLabel, 0, 3);
        formPane.add(itemSizeField, 1, 3);

        formPane.add(itemPriceLabel, 0, 4);
        formPane.add(priceSpinner, 1, 4);
        
        buttonContainer.getChildren().add(submitButton);
        buttonContainer.setMargin(submitButton, new Insets(10));
        
        buttonContainer.getChildren().add(editButton);
        buttonContainer.setMargin(editButton, new Insets(10));
        
        buttonContainer.getChildren().add(deleteButton);
        buttonContainer.setMargin(deleteButton, new Insets(10));

        formPane.add(buttonContainer, 0, 5);

        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);
       

        headerSection.setAlignment(header, Pos.CENTER);
        headerSection.setTop(menuBar);
        headerSection.setBottom(header);

        borderPane.setTop(headerSection);
        borderPane.setCenter(formPane);
        borderPane.setBottom(table);
    }
    
    private void setEventHandler() {
    	submitButton.setOnAction(this);
    	editButton.setOnAction(this);
    	deleteButton.setOnAction(this);
    }
    
    private EventHandler<MouseEvent> setMouseEvent() {
    	return new EventHandler<MouseEvent>() {
    		
    		@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				
    			TableSelectionModel<Item> tableSelectionModel = table.getSelectionModel();
    			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
    			Item item = tableSelectionModel.getSelectedItem();
    			
    			// DISESUAIKAN DENGAN FIELD DI MODEL
    			itemNameField.setText(item.getItem_name());
    			itemCategoryField.setText(item.getItem_category());
    			itemSizeField.setText(item.getItem_size());
    			//priceSpinner.setValue(item.getItem_price());
    			
    			temp_id = item.getItem_id();
			}
    	};
	
    }

    public Scene getScene() {
        return scene;
    }

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == submitButton) {
			String itemName = itemNameField.getText();
			String itemCategory = itemCategoryField.getText();
			String itemSize = itemSizeField.getText();
			int itemPrice = priceSpinner.getValue();
			
			//addData()
			//refreshTable();
		} else if (event.getSource() == editButton) {
			String itemName = itemNameField.getText();
			String itemCategory = itemCategoryField.getText();
			String itemSize = itemSizeField.getText();
			int itemPrice = priceSpinner.getValue();
			
			//refreshTable();
		}
		
	}
}
