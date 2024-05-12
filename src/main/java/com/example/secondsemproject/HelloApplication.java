package com.example.secondsemproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            stage.initStyle(StageStyle.DECORATED.UNDECORATED);

            stage.setTitle("Hello!");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}