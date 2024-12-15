package views;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.User;
import services.Response;


public class RegisterPage implements EventHandler<ActionEvent> {
	
	
	private Scene scene;
	private BorderPane borderContainer;
	private GridPane gridContainer;
	    
	private Label titleLbl, usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, rolesLbl;
	private TextField usernameTF, phoneNumberTF, addressTF;
	private PasswordField passwordPF;
	private Button registerBtn;
	private RadioButton buyerRB, sellerRB;
	private ToggleGroup roleGroup;
	
	private Hyperlink loginLink; 
	
	private PageManager pageManager;
	 
	public RegisterPage(PageManager pageManager) {
		this.pageManager = pageManager;
		initialize();
	    addComponent();
	    setEventHandler();
	}
	 
	private void initialize() {
        borderContainer = new BorderPane();
        gridContainer = new GridPane();
	        
        titleLbl = new Label("Please Register");
        usernameLbl = new Label("Username");
        passwordLbl = new Label("Password");
        phoneNumberLbl = new Label("Phone Number");
        addressLbl = new Label("Address");
        rolesLbl = new Label("Role");
	        
        usernameTF = new TextField();
        usernameTF.setPromptText("Enter your username");
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText("Enter your phone number");
        addressTF = new TextField();
        addressTF.setPromptText("Enter your address");
	        
        passwordPF = new PasswordField();
        passwordPF.setPromptText("Enter your password");
	        
        // Role as Radio Buttons
        buyerRB = new RadioButton("Buyer");
        sellerRB = new RadioButton("Seller");
        roleGroup = new ToggleGroup();
        buyerRB.setToggleGroup(roleGroup);
        sellerRB.setToggleGroup(roleGroup);
        buyerRB.setSelected(true); // default selection
	        
        registerBtn = new Button("Register");
        loginLink = new Hyperlink("Already have an account? Login here");
	        
        scene = new Scene(borderContainer, 500, 400);
	}
	 
	private void addComponent() {
        borderContainer.setTop(titleLbl);
        borderContainer.setCenter(gridContainer);

        // Bottom: Register button and login link
        HBox bottomBox = new HBox(10, registerBtn, loginLink);
        bottomBox.setAlignment(Pos.CENTER);
        borderContainer.setBottom(bottomBox);
	        
        gridContainer.add(usernameLbl, 0, 0);
        gridContainer.add(usernameTF, 1, 0);
	        
        gridContainer.add(passwordLbl, 0, 1);
        gridContainer.add(passwordPF, 1, 1);
	        
        gridContainer.add(phoneNumberLbl, 0, 2);
        gridContainer.add(phoneNumberTF, 1, 2);
	        
        gridContainer.add(addressLbl, 0, 3);
        gridContainer.add(addressTF, 1, 3);
	        
        gridContainer.add(rolesLbl, 0, 4);
        HBox roleBox = new HBox(10, buyerRB, sellerRB);
        gridContainer.add(roleBox, 1, 4);
	}
	 
	private void setEventHandler() {
		registerBtn.setOnAction(this);
		loginLink.setOnAction(e -> {
			// Navigate back to login page
			pageManager.showLoginPage();
		});
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == registerBtn) {
			String username = usernameTF.getText().trim();
			String password = passwordPF.getText().trim();
			String phone = phoneNumberTF.getText().trim();
			String address = addressTF.getText().trim();
			
			String role;
			if (buyerRB.isSelected()) {
				role = "buyer";
			} else {
				role = "seller";
			}
			
			if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
				showAlert(Alert.AlertType.WARNING, "Register Warning", "All fields must be filled!");
				return;
			}
			
			// Call UserController.Register
			Response<User> res = UserController.Register(username, password, phone, address, role);
			if (res.getIsSuccess()) {
				showAlert(Alert.AlertType.INFORMATION, "Register Successful", "You have successfully registered. Please login.");
				pageManager.showLoginPage(); 
			} else {
				showAlert(Alert.AlertType.ERROR, "Register Failed", res.getMessages() != null ? res.getMessages() : "Registration failed.");
			}
		}
	}
	
	private void showAlert(Alert.AlertType type, String title, String content) {
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
