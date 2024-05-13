package com.example.secondsemproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);

        showLoginPage();
    }

    private void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            HelloController loginController = loader.getController();
            loginController.setHelloApplication(this);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Page");
            primaryStage.show();
            primaryStage.setResizable(false);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());

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
