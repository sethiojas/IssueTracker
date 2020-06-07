package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import java.io.IOException;
import com.issue_tracker.Admin;

public class AdminController {
    private Admin admin = new Admin();
    
    @FXML
    private Label unameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox displayVbox;

    @FXML
    public void showManagers(){
        displayVbox.getChildren().clear();

        Button addManager = new Button("+");
        addManager.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/add_contributor.fxml").toURI().toURL());
                Parent root = loader.load();
                AddContributorController cont = loader.getController();
                cont.initialize(unameLabel.getText(), "manager");
                addManager.getScene().setRoot(root);
            }catch(IOException excep){
                excep.printStackTrace();
            }
        });
        displayVbox.getChildren().add(addManager);

        for (String manager: admin.getAllManagers()){
            Button btn = new Button(manager);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
        }
    }

    @FXML
    public void showMaintainers(){
        displayVbox.getChildren().clear();

        for (String maintainer: admin.getAllAssigned()){
            Button btn = new Button(maintainer);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
        }
    }

    @FXML
    public void showOnBench(){
        displayVbox.getChildren().clear();

        Button addMaintainer = new Button("+");
        addMaintainer.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/add_contributor.fxml").toURI().toURL());
                Parent root = loader.load();
                AddContributorController cont = loader.getController();
                cont.initialize(unameLabel.getText(), "maintainer");
                addMaintainer.getScene().setRoot(root);
            }catch(IOException excep){
                excep.printStackTrace();
            }
        });
        displayVbox.getChildren().add(addMaintainer);

        for (String maintainer: admin.getAllOnBench()){
            Button btn = new Button(maintainer);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
        }
    }

    @FXML
    public void logout(){
        try{
            Parent root = FXMLLoader.load(new File("../fxml/login.fxml").toURI().toURL());
            logoutButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    public void initialize(String uname) {
        unameLabel.setText(uname);
        showManagers();
    }
}