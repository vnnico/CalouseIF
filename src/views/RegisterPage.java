package views;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
        addComponents();
        setEventHandlers();
    }
     
 
    private void initialize() {
        borderContainer = new BorderPane();
        gridContainer = new GridPane();
        
    
        titleLbl = new Label("Please Register");
        titleLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
      
        usernameLbl = new Label("Username:");
        passwordLbl = new Label("Password:");
        phoneNumberLbl = new Label("Phone Number:");
        addressLbl = new Label("Address:");
        rolesLbl = new Label("Role:");
        
   
        usernameTF = new TextField();
        usernameTF.setPromptText("Enter your username");
        usernameTF.setPrefWidth(200);
        
        passwordPF = new PasswordField();
        passwordPF.setPromptText("Enter your password");
        passwordPF.setPrefWidth(200);
        
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText("Enter your phone number");
        phoneNumberTF.setPrefWidth(200);
        
        addressTF = new TextField();
        addressTF.setPromptText("Enter your address");
        addressTF.setPrefWidth(200);

        buyerRB = new RadioButton("Buyer");
        sellerRB = new RadioButton("Seller");
        roleGroup = new ToggleGroup();
        buyerRB.setToggleGroup(roleGroup);
        sellerRB.setToggleGroup(roleGroup);
        buyerRB.setSelected(true); 
        
    
        registerBtn = new Button("Register");
        registerBtn.setPrefWidth(100);
        
        // Login Hyperlink
        loginLink = new Hyperlink("Already have an account? Login here");
    }
     

    private void addComponents() {
     
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
        col1.setHalignment(HPos.RIGHT); // Right-align labels
        col1.setPrefWidth(120);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.LEFT); // Left-align fields
        col2.setPrefWidth(250);
        
        gridContainer.getColumnConstraints().addAll(col1, col2);
        
        // Add Labels and Fields to GridPane
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
        roleBox.setAlignment(Pos.CENTER_LEFT);
        gridContainer.add(roleBox, 1, 4);
        
        borderContainer.setCenter(gridContainer);
        
        // Bottom Section - Register Button and Login Link
        HBox bottomBox = new HBox(15, registerBtn, loginLink);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0)); // Top padding to separate from form
        borderContainer.setBottom(bottomBox);
        
        // Create Scene with a reasonable size
        scene = new Scene(borderContainer, 500, 450);
    }
     
    /**
     * Set event handlers for interactive components.
     */
    private void setEventHandlers() {
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
            
            String role = buyerRB.isSelected() ? "buyer" : "seller";
       
            // Register Controller
            Response<User> res = UserController.Register(username, password, phone, address, role);
            if (res.getIsSuccess()) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered. Please login.");
                pageManager.showLoginPage(); 
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", res.getMessages() != null ? res.getMessages() : "Registration failed.");
            }
        }
    }
    

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(borderContainer.getScene().getWindow()); // Set the owner to current window
        alert.showAndWait();
    }
    
   
    public Scene getScene() {
        return scene;
    }

}
