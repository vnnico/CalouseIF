package main;

import javafx.application.*;
import javafx.stage.Stage;
import views.PageManager;
import views.RegisterPage;
import views.Buyer.BuyerHomePage;
import views.Seller.*;


import views.Seller.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       

    	 try {
             PageManager pageManager = new PageManager(primaryStage);
             pageManager.showLoginPage();
             primaryStage.setTitle("CaLouseIF");
             primaryStage.show();
         } catch(Exception e) {
             e.printStackTrace();
         }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
