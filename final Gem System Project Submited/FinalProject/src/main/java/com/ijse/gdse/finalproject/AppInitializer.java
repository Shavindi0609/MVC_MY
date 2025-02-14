package com.ijse.gdse.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Hello");
        Parent load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("Gem World");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }


}
