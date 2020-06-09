package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.io.IOException;
import java.io.File;
import com.issue_tracker.Manager;
import com.issue_tracker.Contributor;
import java.util.Map;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style; 

/**Controller associated with manager.fxml 
*/

public class ManagerController extends MaintainerController implements HasProjects {
    private String contributorUName;
    private Manager me;
    
    @FXML
    private Label managerUName;
    
    @FXML
    private Button projectsButton;

    @FXML
    private Button maintainersButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane managerCenter;

    @FXML
    private VBox centerVbox;

    @FXML
    public void changePassword() {
        /**Change password of Manager 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/change_password.fxml").toURI().toURL());
            AnchorPane root = loader.load();
            PasswordController cont = loader.getController();
            cont.initialize(contributorUName);
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
    public void showProjects(){
        /**Show all the projects manager has. 
        */
        centerVbox.getChildren().clear();
        Label label = new Label("Projects");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(32);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold; "+
                        "-fx-text-fill: white; -fx-background-color: #222831;");
        centerVbox.getChildren().add(label);

        // Button to create a new project
        Button addProject = new Button("+");
        addProject.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/new_project.fxml").toURI().toURL());
                AnchorPane root = loader.load();
                NewProjectController cont = loader.getController();
                cont.initialize(contributorUName);
                addProject.getScene().setRoot(root);
            }catch(IOException excep){
                excep.printStackTrace();
            }
        });
        centerVbox.getChildren().add(addProject);

        // Key is project title and value is project id
        for (Map.Entry<String, Integer> entry : me.getProjects().entrySet()) {
            HBox root = projectRow(entry);
            centerVbox.getChildren().add(root);
        }
    }

    public HBox projectRow(Map.Entry<String, Integer> entry){
        /**Return a row consisting of project and buttons of actions
        *which can be performed on that project 
        */
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);

        Button btn = new Button(entry.getKey());
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);
        
        HBox.setHgrow(btn, Priority.ALWAYS);

        // On click, show project info
        btn.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../fxml/project.fxml").toURI().toURL());
                BorderPane view = loader.load();
                ProjectController cont = loader.getController();
                cont.initialize(entry.getValue(), contributorUName, "../fxml/manager.fxml");
                btn.getScene().setRoot(view);
            }catch(IOException excep) {
                excep.printStackTrace();
            }
        });

        // button to delete project
        Button remove = new Button("Remove");
        remove.setOnAction(e -> {
            me.removeProject(entry.getKey());
            showProjects();
        });

        root.getChildren().addAll(btn, remove);
        return root;
    }

    public void showMaintainers() {
        /**Show all maintainers assigned to manager 
        */
        centerVbox.getChildren().clear();
        Label label = new Label("Maintainers");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(32);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold; " + 
                       "-fx-text-fill: white; -fx-background-color: #222831;");
        centerVbox.getChildren().add(label);

        for (String maintainerUname : me.getMaintainers()) {
            Button btn = new Button(maintainerUname);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            // on click, show info about maintainer
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File(
                        // https://stackoverflow.com/questions/17228487/javafx-location-is-not-set-error-message
                        "../fxml/manager_selection_maintainers.fxml"
                        ).toURI().toURL()
                    );
                    GridPane root = loader.load();
                    MaintainerDetailsController cont = loader.getController();
                    cont.initialize(maintainerUname, contributorUName);
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep) {
                    excep.printStackTrace();
                }
            });
            centerVbox.getChildren().add(btn);
        }
    }

    @Override
    public void initialize(String uname) {
        /**Initialize class members and show all projects
        *which manager has (default view) 
        */
        contributorUName = uname;
        managerUName.setText(contributorUName);
        me = (Manager) Contributor.getContributor(uname);
        showProjects();
    }
}