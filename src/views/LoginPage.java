package views;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.User;
import services.Response;


public class LoginPage implements EventHandler<ActionEvent> {

    private Scene scene;
    private BorderPane borderContainer;
    private GridPane gridContainer;

    private Label titleLbl, usernameLbl, passwordLbl;
    private TextField usernameTF;
    private PasswordField passwordPF;
    private Button loginBtn;
    private Hyperlink registerLink; 
    
    private PageManager pageManager;

    public LoginPage(PageManager pageManager) {
    	this.pageManager = pageManager;
        initialize();
        addComponent();
        setEventHandler();
    }

    private void initialize() {
        borderContainer = new BorderPane();
        gridContainer = new GridPane();


        // title Label
        titleLbl = new Label("Login to Your Account");
        titleLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

  
        // Field Label
        usernameLbl = new Label("Username:");
        passwordLbl = new Label("Password:");

       
        usernameTF = new TextField();
        usernameTF.setPromptText("Enter your username");
        usernameTF.setPrefWidth(200);

        passwordPF = new PasswordField();
        passwordPF.setPromptText("Enter your password");
        passwordPF.setPrefWidth(200);

       
        loginBtn = new Button("Login");
        loginBtn.setPrefWidth(100);

        
        // Hyperlink
        registerLink = new Hyperlink("Not registered? Register here");
    }

    private void addComponent() {
      
        borderContainer.setPadding(new Insets(20, 20, 20, 20));

        // Top Section - Title
        HBox topBox = new HBox(titleLbl);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 20, 0)); 
        borderContainer.setTop(topBox);

        // Center Section - Form GridPane
        gridContainer.setHgap(10);
        gridContainer.setVgap(15);
        gridContainer.setAlignment(Pos.CENTER);

        // Define Column Constraints for GridPane
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);
        col1.setPrefWidth(100);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.LEFT); 
        col2.setPrefWidth(200);

        gridContainer.getColumnConstraints().addAll(col1, col2);

        // Add Labels and Fields to GridPane
        gridContainer.add(usernameLbl, 0, 0);
        gridContainer.add(usernameTF, 1, 0);

        gridContainer.add(passwordLbl, 0, 1);
        gridContainer.add(passwordPF, 1, 1);

        borderContainer.setCenter(gridContainer);

        // Bottom Section - Login Button and Register Link
        HBox bottomBox = new HBox(15, loginBtn, registerLink);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0)); 
        borderContainer.setBottom(bottomBox);

        // Scene Creation
        scene = new Scene(borderContainer, 400, 250);
    }
    
    
    
    private void setEventHandler() {
    	loginBtn.setOnAction(this);
    	
    	// Navigate to registration page
    	registerLink.setOnAction(event -> {
    
             pageManager.showRegisterPage();
         });
    }

    public Scene getScene() {
    	return scene;
    }

	@Override
	public void handle(ActionEvent event) {
		
		 if (event.getSource() == loginBtn) {
			 // Retrieve entered username and password
	            String username = usernameTF.getText().trim();
	            String password = passwordPF.getText().trim();

	            if (username.isEmpty() || password.isEmpty()) {
	                showAlert(Alert.AlertType.WARNING, "Login Warning", "Please enter both username and password.");
	                return;
	            }

	            
	            // Navigate to admin dashboard
	            if (username.equals("admin") && password.equals("admin")) {
	            	
	                pageManager.showAdminDashboard();
	                return;
	            }

	            
	            // Authenticate user with UserController
	            Response<User> res = UserController.Login(username, password);
	            if (res.getIsSuccess() && res.getData() != null) {
	                User loggedInUser = res.getData();
	                pageManager.setLoggedInUser(loggedInUser);
	                String role = loggedInUser.getRole();

	                switch (role.toLowerCase()) {
	                    case "admin":
	                        pageManager.showAdminDashboard();
	                        break;
	                    case "seller":
	                        pageManager.showSellerDashboard();
	                        break;
	                    case "buyer":
	                        pageManager.showBuyerDashboard();
	                        break;
	                    default:
	                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid user role: " + role);
	                        break;
	                }
	            } else {
	            	
	            	// Error message if login fails
	                showAlert(Alert.AlertType.ERROR, "Login Failed", res.getMessages() != null ? res.getMessages() : "Invalid username or password.");
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
    
};
