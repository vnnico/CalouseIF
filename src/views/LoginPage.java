package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class LoginPage implements EventHandler<ActionEvent> {

    private Scene scene;
    private BorderPane borderContainer;
    private GridPane gridContainer;

    private Label titleLbl, usernameLbl, passwordLbl;
    private TextField usernameTF;
    private PasswordField passwordPF;
    private Button loginBtn;
    
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

        scene = new Scene(borderContainer, 400, 200);
    }

    private void addComponent() {
        borderContainer.setTop(titleLbl);
        borderContainer.setCenter(gridContainer);
        borderContainer.setBottom(loginBtn);

        gridContainer.add(usernameLbl, 0, 0);
        gridContainer.add(usernameTF, 1, 0);

        gridContainer.add(passwordLbl, 0, 1);
        gridContainer.add(passwordPF, 1, 1);
    }
    
    
    
    private void setEventHandler() {
    	loginBtn.setOnAction(this);
    }

    public Scene getScene() {
    	return scene;
    }

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getSource()== loginBtn) {

			String username = usernameTF.getText().trim();
	        String password = passwordPF.getText().trim();

	        if (username.isEmpty() || password.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Login Warning");
	            alert.setHeaderText(null);
	            alert.setContentText("Please enter both username and password.");
	            alert.showAndWait();
	        } else {
//	          boolean success = pageManager.handleLogin(username, password);
//	          if (!success) {
//	              Alert alert = new Alert(Alert.AlertType.ERROR);
//	              alert.setTitle("Login Failed");
//	              alert.setHeaderText(null);
//	              alert.setContentText("Invalid username or password.");
//	              alert.showAndWait();
//	          }
	        	System.out.println("Masuk broo");
	        	pageManager.handleLogin();
	            // Jika login berhasil, PageManager sudah melakukan navigasi
	        }
	    

		/*
		 * goToRegisterBtn.setOnAction((ActionEvent e) -> {
		 * pageManager.showRegisterPage(); });
		 */
		}
		}
		
    
};
