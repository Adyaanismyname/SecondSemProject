package com.example.secondsemproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
        showLoginPage();
    }

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
            primaryStage.setResizable(false);


            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    offsetX = event.getSceneX();
                    offsetY = event.getSceneY(); // Using getSceneY() to get the y-coordinate relative to the scene
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    primaryStage.setX(event.getScreenX() - offsetX);
                    primaryStage.setY(event.getScreenY() - offsetY); // Corrected to set Y coordinate
                }
            });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            primaryStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
