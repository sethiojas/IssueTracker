package com.issue_tracker.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import java.io.File;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**Main JavaFX Application 
*/

public class MainApp extends Application {
      
    @Override
    public void start(Stage primaryStage) throws Exception{
        /**Start metjod of appication
        *@param stage   primary stage of application
        *@throws        Exception 
        */
        GridPane root = FXMLLoader.load(new File("../fxml/login.fxml").toURI().toURL());
        JMetro jmetro = new JMetro(Style.LIGHT);
        Scene scene = new Scene(root);
        jmetro.setScene(scene);
        primaryStage.setTitle("IssueTracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        /**Main method
        *@params args   arguments passed to program 
        */
        launch(args);
    }
}
