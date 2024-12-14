package views;

import javafx.scene.Scene;
import javafx.stage.Stage;
import views.Buyer.BuyerHomePage;
import views.Buyer.ViewPurchasePage;
import views.Buyer.ViewWishlistPage;
import views.Seller.OfferedItemPage;
import views.Seller.SellerHomePage;

public class PageManager {

	private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;
    private Scene adminDashboardScene;
    private Scene sellerDashboardScene;
    private Scene buyerDashboardScene;
    private Scene viewWishlistScene;
    private Scene viewPurchaseScene;
    private Scene offeredItemScene;
    
    public PageManager(Stage stage) {
    	 this.primaryStage = stage;
    	 initPages();
//         userDatabase = new HashMap<>();
//         initializeUsers(); // Prepopulate some users
//         initPages();
    }
    
//    private void initializeUsers() {
//        // Prepopulate dengan beberapa pengguna
//        userDatabase.put("admin", new User("admin", "adminpass", "admin"));
//        userDatabase.put("seller", new User("seller", "sellerpass", "seller"));
//        userDatabase.put("buyer", new User("buyer", "buyerpass", "buyer"));
//    }
    
    private void initPages() {
       
        LoginPage loginPage = new LoginPage(this);
        RegisterPage registerPage = new RegisterPage(this);
        
        //AdminDashboardPage adminDashboard = new AdminDashboardPage(this);
        SellerHomePage sellerDashboard = new SellerHomePage(this);
        BuyerHomePage buyerDashboard = new BuyerHomePage(this);
        
        ViewWishlistPage viewWishlistPage = new ViewWishlistPage(this);
        ViewPurchasePage viewPurchasePage = new ViewPurchasePage(this);
        OfferedItemPage offeredItemPage = new OfferedItemPage(this);
        
        
        loginScene = loginPage.getScene();
        registerScene = registerPage.getScene();
        //adminDashboardScene = adminDashboard.getScene();
        sellerDashboardScene = sellerDashboard.getScene();
        buyerDashboardScene = buyerDashboard.getScene();
        viewWishlistScene = viewWishlistPage.getScene();
        viewPurchaseScene = viewPurchasePage.getScene();
        offeredItemScene = offeredItemPage.getScene();
    }
    
    public void showLoginPage() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
    }

    public void showRegisterPage() {
        primaryStage.setScene(registerScene);
        primaryStage.setTitle("Register");
    }

//    public void showAdminDashboard() {
//        primaryStage.setScene(ad);
//        primaryStage.setTitle("Admin Dashboard");
//    }

    public void showSellerDashboard() {
        primaryStage.setScene(sellerDashboardScene);
        primaryStage.setTitle("Seller Dashboard");
    }

    public void showBuyerDashboard() {
        primaryStage.setScene(buyerDashboardScene);
        primaryStage.setTitle("Buyer Dashboard");
    }
    
    public void showViewWishlist() {
        primaryStage.setScene(viewWishlistScene);
        primaryStage.setTitle("Wishlist");
    }
    
    public void showViewPurchaseHistory() {
        primaryStage.setScene(viewPurchaseScene);
        primaryStage.setTitle("Purchase History");
    }
    
    public void showOfferedItemPage() {
        primaryStage.setScene(offeredItemScene);
        primaryStage.setTitle("Offered Items");
    }
    
    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    
    // Metode untuk menangani login
    public void handleLogin() {
//        if (userDatabase.containsKey(username)) {
//            User user = userDatabase.get(username);
//            if (user.getPassword().equals(password)) {
//                // Login berhasil, navigasi berdasarkan role
//                switch (user.getRole()) {
//                    case "admin":
//                        showAdminDashboard();
//                        break;
//                    case "seller":
//                        showSellerDashboard();
//                        break;
//                    case "buyer":
//                        showBuyerDashboard();
//                        break;
//                    default:
//                        System.out.println("Role tidak dikenal: " + user.getRole());
//                        return false;
//                }
//                return true;
//            }
//        }
//        return false; // Login gagal
    	System.out.println("SINI");
    	showBuyerDashboard();
    }

    // Metode untuk menangani registrasi
//    public boolean handleRegister(String username, String password, String phone, String address, String role) {
//        if (userDatabase.containsKey(username)) {
//            // Username sudah ada
//            return false;
//        } else {
//            User newUser = new User(username, password, role);
//            userDatabase.put(username, newUser);
//            return true;
//        }
//    }
}
