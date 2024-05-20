package com.example.secondsemproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage primaryStage;
    private double offsetX = 0;
    private double offsetY = 0;
    HelloController helloController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED); // Set stage style to undecorated
        showLoginPage(); // Show login page on startup
    }


    // Method to display the login page
    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            HelloController loginController = loader.getController();
            this.helloController = loginController;
            loginController.setHelloApplication(this);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Page");
            primaryStage.show();
            primaryStage.setResizable(false); // Disable window resizing

            // Event handler to handle window dragging
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    offsetX = event.getSceneX();
                    offsetY = event.getSceneY(); // Get coordinates relative to the scene
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - offsetX);
                    primaryStage.setY(event.getScreenY() - offsetY); // Set window position based on drag
                }
            });

        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for IOException
        }
    }



    // Method to display the home page
    public void showHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.setLoginController(this.helloController);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Home Page");
            primaryStage.show();
            primaryStage.setResizable(false); // Disable window resizing
            System.out.println(HelloController.getUsername_to_pass()); // Debug print statement

        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for IOException
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
