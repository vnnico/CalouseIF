package views;

import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import views.Buyer.BuyerHomePage;
import views.Buyer.ViewPurchasePage;
import views.Buyer.ViewWishlistPage;
import views.Seller.OfferedItemPage;
import views.Seller.SellerHomePage;
import views.Admin.AdminHomePage;
import views.LoginPage;
import views.RegisterPage;

public class PageManager {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;
    
    // Save login user
    private User loggedInUser;
    
    public PageManager(Stage stage) {
        this.primaryStage = stage;
        initStaticPages();
    }
    
    private void initStaticPages() {
    	// Initiate Login and Register Page
        LoginPage loginPage = new LoginPage(this);
        RegisterPage registerPage = new RegisterPage(this);
        
        loginScene = loginPage.getScene();
        registerScene = registerPage.getScene();
    }
    

    // LOGIN PAGE 
    public void showLoginPage() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // REGISTER PAGE 
    public void showRegisterPage() {
        primaryStage.setScene(registerScene);
        primaryStage.setTitle("Register");
        primaryStage.show();
    }

    // ADMIN DASHBOARD (VIEW REQUESTED ITEM)
    public void showAdminDashboard() {
    
        AdminHomePage adminDashboard = new AdminHomePage(this);
        Scene adminDashboardScene = adminDashboard.getScene();
        primaryStage.setScene(adminDashboardScene);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();
    }

    // SELLER DASHBOARD (UPLOAD AND VIEW ITEM)
    public void showSellerDashboard() {
        SellerHomePage sellerDashboard = new SellerHomePage(this);
        Scene sellerDashboardScene = sellerDashboard.getScene();
        primaryStage.setScene(sellerDashboardScene);
        primaryStage.setTitle("Seller Dashboard");
        primaryStage.show();
    }
    
    // BUYER DASHBOARD (BROWSE ITEM, VIEW ITEM)
    public void showBuyerDashboard() {
        BuyerHomePage buyerDashboard = new BuyerHomePage(this);
        Scene buyerDashboardScene = buyerDashboard.getScene();
        primaryStage.setScene(buyerDashboardScene);
        primaryStage.setTitle("Buyer Dashboard");
        primaryStage.show();
    }
    
    // WISHLIST PAGE 
    public void showViewWishlist() {
        if (loggedInUser == null) {
            showLoginPage();
            return;
        }
        ViewWishlistPage viewWishlistPage = new ViewWishlistPage(this);
        Scene viewWishlistScene = viewWishlistPage.getScene();
        primaryStage.setScene(viewWishlistScene);
        primaryStage.setTitle("Wishlist");
        primaryStage.show();
    }
    
    // PURCHASE HISTORY PAGE
    public void showViewPurchaseHistory() {
        if (loggedInUser == null) {
            showLoginPage();
            return;
        }
        ViewPurchasePage viewPurchasePage = new ViewPurchasePage(this);
        Scene viewPurchaseScene = viewPurchasePage.getScene();
        primaryStage.setScene(viewPurchaseScene);
        primaryStage.setTitle("Purchase History");
        primaryStage.show();
    }
    
    // OFFERED ITEM PAGE 
    public void showOfferedItemPage() {
        OfferedItemPage offeredItemPage = new OfferedItemPage(this);
        Scene offeredItemScene = offeredItemPage.getScene();
        primaryStage.setScene(offeredItemScene);
        primaryStage.setTitle("Offered Items");
        primaryStage.show();
    }
    
 
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void clearLoggedInUser() {
        this.loggedInUser = null;
    }
}
