package com.issue_tracker.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import java.io.File;

public class MainApp extends Application {
      
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = FXMLLoader.load(new File("../fxml/login.fxml").toURI().toURL());
        primaryStage.setTitle("IssueTracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
