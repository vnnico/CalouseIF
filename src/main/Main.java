package main;

import javafx.application.*;
import javafx.stage.Stage;
import views.AdminHomePage;
import views.SellerHomePage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       
//       SellerHomePage sellerDashboard = new SellerHomePage();
//
//        primaryStage.setTitle("Seller Dashboard");
//        primaryStage.setScene(sellerDashboard.getScene());
//        primaryStage.show();
    	
    	AdminHomePage adminDashboard = new AdminHomePage();
    	
    	primaryStage.setTitle("Admin Dashboard");
    	primaryStage.setScene(adminDashboard.getScene());
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
