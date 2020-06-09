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
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**Controller class for admin.fxml
*Admins can
*   remove/create Contributors
*   Assign Manager to On Bench Maintainers
*   Put assigned maintainers on bench
*/

public class AdminController {
    private Admin admin = new Admin();
    
    @FXML
    private Label unameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox displayVbox;

    @FXML
    public void changePassword() {
        /**Change password of Manager 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/change_password.fxml").toURI().toURL());
            Parent root = loader.load();
            PasswordController cont = loader.getController();
            cont.initialize(unameLabel.getText());
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
    public void showManagers(){
        /**Displays a list of all managers
        *Managers can be removed or added via this panel
        */
        displayVbox.getChildren().clear();

        Label managerLabel = new Label("Managers");
        managerLabel.setMaxWidth(Double.MAX_VALUE);
        managerLabel.setMaxHeight(32);
        managerLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; "+
                        "-fx-text-fill: white; -fx-background-color: #222831;");
        displayVbox.getChildren().add(managerLabel);
        
        // create new manager button
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
        /**Creates a row for single manager along with buttons corresponding
        *to actions that can be performed on that manager.
        *@param manager  Uname of manager
        *@returns        HBox containg buttons
        */
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        Button btn = new Button(manager);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        // remove manager account button
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
        /**Displays all maintainers in database which are assigned
        *to some manager
        */
        displayVbox.getChildren().clear();

        Label maintainerLabel = new Label("Maintainers");
        maintainerLabel.setMaxWidth(Double.MAX_VALUE);
        maintainerLabel.setMaxHeight(32);
        maintainerLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; "+
                        "-fx-text-fill: white; -fx-background-color: #222831;");
        displayVbox.getChildren().add(maintainerLabel);

        for (String maintainer: admin.getAllAssigned()){
            HBox root = maintainerRow(maintainer);
            displayVbox.getChildren().add(root);
        }
    }

    public HBox maintainerRow(String maintainer){
        /**Creates a row given maintainer along with buttons corresponding
        *to actions which can be performed on that maintainer
        *@param maintainer  Uname of maintainer
        *@returns           HBox containing buttons
        */
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        Button btn = new Button(maintainer);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        //Put maintainer on bench button
        Button benchMaintainer = new Button("Bench");
        benchMaintainer.setOnAction(e -> {
            Maintainer m = (Maintainer) Contributor.getContributor(maintainer);
            admin.putMaintainerOnBench(m.getManager(), maintainer);
            showMaintainers();
        });

        // remove maintainer button
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
        /**Shows all Maintainers currently on bench 
        *New maintainers can also be created via this panel 
        */
        displayVbox.getChildren().clear();

        Label onBenchLabel = new Label("On-Bench");
        onBenchLabel.setMaxWidth(Double.MAX_VALUE);
        onBenchLabel.setMaxHeight(32);
        onBenchLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; "+
                        "-fx-text-fill: white; -fx-background-color: #222831;");
        displayVbox.getChildren().add(onBenchLabel);

        // create new maintainer button
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
        /**Creates a row for an On-Bench maintainer along with buttons
        *corresponding to actions which can be performed on that maintainer 
        *@param name    Uname of on-bench maintainer
        *@returns       HBox containing buttons
        */
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);

        Button maintainer = new Button(name);
        maintainer.setMaxWidth(Double.MAX_VALUE);
        maintainer.setMaxHeight(Double.MAX_VALUE);
        maintainer.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(maintainer, Priority.ALWAYS);

        // assign manager to an on-bench maintainer button
        Button assign = new Button("Assign");
        assign.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/assign_manager.fxml").toURI().toURL());
                Parent view = loader.load();
                AssignManagerController cont = loader.getController();
                cont.initialize(unameLabel.getText(), name);
                Stage stage = new Stage();
                Scene scene = new Scene(view);
                JMetro jmetro = new JMetro(Style.LIGHT);
                jmetro.setScene(scene);
                stage.setScene(scene);
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

        // remove on-bench maintainer account button
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
        /**Logout from current account
        *Takes user back to Login screen 
        */
        try{
            Parent root = FXMLLoader.load(new File("../fxml/login.fxml").toURI().toURL());
            logoutButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    public void initialize(String uname) {
        /**Set text of unameLabel and call show managers (default panel)
        */
        unameLabel.setText(uname);
        showManagers();
    }
}