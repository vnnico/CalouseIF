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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

        titleLbl = new Label("Login to Your Account");
        usernameLbl = new Label("Username");
        passwordLbl = new Label("Password");

        usernameTF = new TextField();
        usernameTF.setPromptText("Enter your username");

        passwordPF = new PasswordField();
        passwordPF.setPromptText("Enter your password");

        loginBtn = new Button("Login");
        
        registerLink = new Hyperlink("Not registered? Register here");

        scene = new Scene(borderContainer, 400, 200);
    }

    private void addComponent() {
        borderContainer.setTop(titleLbl);
        borderContainer.setCenter(gridContainer);
        
        HBox bottomBox = new HBox(10, loginBtn, registerLink);
        bottomBox.setAlignment(Pos.CENTER);
        borderContainer.setBottom(bottomBox);

        gridContainer.add(usernameLbl, 0, 0);
        gridContainer.add(usernameTF, 1, 0);

        gridContainer.add(passwordLbl, 0, 1);
        gridContainer.add(passwordPF, 1, 1);
    }
    
    
    
    private void setEventHandler() {
    	loginBtn.setOnAction(this);
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
	            String username = usernameTF.getText().trim();
	            String password = passwordPF.getText().trim();

	            if (username.isEmpty() || password.isEmpty()) {
	                showAlert(Alert.AlertType.WARNING, "Login Warning", "Please enter both username and password.");
	                return;
	            }

	       
	            if (username.equals("admin") && password.equals("admin")) {
	                pageManager.showAdminDashboard();
	                return;
	            }

	         
	            Response<User> res = UserController.Login(username, password);
	            if (res.getIsSuccess() && res.getData() != null) {
	                User loggedInUser = res.getData();
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
	                // Login failed
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
