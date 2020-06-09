package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.io.File;
import com.issue_tracker.Maintainer;
import com.issue_tracker.Contributor;
import java.util.Map;
import java.util.HashMap;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**Controller class for maintainer.fxml 
*/

public class MaintainerController implements HasProjects{
    
    @FXML
    private Label uNameLabel;
    
    @FXML
    private Label managerLabel;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button logoutButton;

    @FXML
    public void changePassword() {
        /**Change password of Maintainer 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/change_password.fxml").toURI().toURL());
            AnchorPane root = loader.load();
            PasswordController cont = loader.getController();
            cont.initialize(uNameLabel.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            JMetro jmetro = new JMetro(Style.LIGHT);
            jmetro.setScene(scene);
            stage.setTitle("Change Password");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent event){
        /**Log out of current account.
        *Screen is changed to login screen upon logout 
        */
        try{
            GridPane root = FXMLLoader.load(new File("../fxml/login.fxml").toURI().toURL());
            logoutButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    @Override
    public void initialize(String uname){
        /**Initialise the view of Maintainer panel
        *Every project assigned to maintainer along
        *with the uname of manager to which maintainer
        *is assigned is displayed on this screen
        *@param uname   Uname of maintainer 
        */
        Maintainer me = (Maintainer) Contributor.getContributor(uname);

        // Show uname of manager to which maintainer is assigned
        uNameLabel.setText(uname);
        managerLabel.setText(me.getManager());

        // show every assigned project
        for (Map.Entry<String, Integer> entry: me.getProjects().entrySet()){
            Button btn = new Button(entry.getKey());
            btn.setAlignment(Pos.BASELINE_LEFT);
            //or btn.setStyle("-fx-alignment: LEFT;");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File("../fxml/project.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    ProjectController cont = loader.getController();
                    cont.initialize(entry.getValue(), uname, "../fxml/maintainer.fxml");
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep){
                    excep.printStackTrace();
                    return;
                }
            });
            projectsVbox.getChildren().add(btn);
        }
    }
}