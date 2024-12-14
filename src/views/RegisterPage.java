package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class RegisterPage implements EventHandler<ActionEvent> {
	
	 private Scene scene;
	 private BorderPane borderContainer;
	 private GridPane gridContainer;
	    
	 private Label titleLbl, usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, rolesLbl;
	 private TextField usernameTF, phoneNumberTF, addressTF, rolesTF;
	 private PasswordField passwordPF;
	 private Button registerBtn;
	 
	 private PageManager pageManager;
	 
	 public RegisterPage(PageManager pageManager) {
		 this.pageManager = pageManager;
		 initialize();
	     addComponent();
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
	        rolesTF = new TextField();
	        rolesTF.setPromptText("Enter your role");
	        
	        passwordPF = new PasswordField();
	        passwordPF.setPromptText("Enter your password");
	        
	        registerBtn = new Button("Register");
	        scene = new Scene(borderContainer, 500, 300);
	    }
	 
	 private void addComponent() {
	        borderContainer.setTop(titleLbl);
	        borderContainer.setCenter(gridContainer);
	        borderContainer.setBottom(registerBtn);
	        
	        gridContainer.add(usernameLbl, 0, 0);
	        gridContainer.add(passwordLbl, 0, 1);
	        gridContainer.add(phoneNumberLbl, 0, 2);
	        gridContainer.add(addressLbl, 0, 3);
	        gridContainer.add(rolesLbl, 0, 4);
	        
	        gridContainer.add(usernameTF, 1, 0);
	        gridContainer.add(passwordPF, 1, 1);
	        gridContainer.add(phoneNumberTF, 1, 2);
	        gridContainer.add(addressTF, 1, 3);
	        gridContainer.add(rolesTF, 1, 4);
	    }
	 
	 
	 public Scene getScene() {
		 return scene;
	 }
	 
	    
	 private void setEventHandler() {
		 registerBtn.setOnAction(this);
	 }

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == registerBtn) {
			
		}
	}


}
