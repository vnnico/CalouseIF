package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.Seller.*;

import views.Seller.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       
        SellerHomePage sellerDashboard = new SellerHomePage();

        primaryStage.setTitle("Seller Dashboard");
        primaryStage.setScene(sellerDashboard.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
