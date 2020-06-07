package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import java.io.IOException;
import com.issue_tracker.Admin;
import com.issue_tracker.Contributor;
import com.issue_tracker.Maintainer;

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
            HBox root = managerRow(manager);
            displayVbox.getChildren().add(root);
        }
    }

    public HBox managerRow(String manager){
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        Button btn = new Button(manager);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            admin.removeManager(manager);
            showManagers();
        });

        root.getChildren().addAll(btn, remove);
        return root;
    }

    @FXML
    public void showMaintainers(){
        displayVbox.getChildren().clear();

        for (String maintainer: admin.getAllAssigned()){
            HBox root = maintainerRow(maintainer);
            displayVbox.getChildren().add(root);
        }
    }

    public HBox maintainerRow(String maintainer){
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        Button btn = new Button(maintainer);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Button benchMaintainer = new Button("Bench");
        benchMaintainer.setOnAction(e -> {
            Maintainer m = (Maintainer) Contributor.getContributor(maintainer);
            admin.putMaintainerOnBench(m.getManager(), maintainer);
            showMaintainers();
        });

        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            Maintainer m = (Maintainer) Contributor.getContributor(maintainer);
            admin.removeMaintainer(maintainer, m.getManager());
            showMaintainers();
        });

        root.getChildren().addAll(btn, benchMaintainer, remove);
        return root;
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
        
        for (String name: admin.getAllOnBench()) {
            HBox root = onBenchRow(name);
            displayVbox.getChildren().add(root);
        }
    }

    public HBox onBenchRow(String name) {
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);

        Button maintainer = new Button(name);
        maintainer.setMaxWidth(Double.MAX_VALUE);
        maintainer.setMaxHeight(Double.MAX_VALUE);
        maintainer.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(maintainer, Priority.ALWAYS);

        Button assign = new Button("Assign");
        assign.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/assign_manager.fxml").toURI().toURL());
                Parent view = loader.load();
                AssignManagerController cont = loader.getController();
                cont.initialize(unameLabel.getText(), name);
                Stage stage = new Stage();
                stage.setScene(new Scene(view));
                stage.setTitle("Issue Tracker");
                stage.setResizable(false);
                stage.setOnHidden(event -> {
                    showOnBench();
                });
                stage.show();
            }catch(IOException excep){
                excep.printStackTrace();
            }
        });

        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            admin.removeMaintainer(name);
            showOnBench();
        });

        root.getChildren().addAll(maintainer, assign, remove);
        return root;
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