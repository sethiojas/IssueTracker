package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.io.IOException;
import java.io.File;
import com.issue_tracker.Manager;
import com.issue_tracker.Contributor;
import java.util.Map;

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
    public void showProjects(){
        centerVbox.getChildren().clear();
        Label label = new Label("Projects");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        centerVbox.getChildren().add(label);

        Button addProject = new Button("+");
        addProject.setOnAction(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(new File("../../fxml/new_project.fxml").toURI().toURL());
                AnchorPane root = loader.load();
                NewProjectController cont = loader.getController();
                cont.initialize(contributorUName);
                addProject.getScene().setRoot(root);
            }catch(IOException excep){
                excep.printStackTrace();
            }
        });
        centerVbox.getChildren().add(addProject);
       
        for (Map.Entry<String, Integer> entry : me.getProjects().entrySet()) {
            Button btn = new Button(entry.getKey());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File("../../fxml/project.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    ProjectController cont = loader.getController();
                    cont.initialize(entry.getValue(), contributorUName, "../../fxml/manager.fxml");
                    btn.getScene().setRoot(root);
                }catch(IOException excep) {
                    excep.printStackTrace();
                }
            });
            centerVbox.getChildren().add(btn);
        }
    }

    public void showMaintainers() {
        centerVbox.getChildren().clear();
        Label label = new Label("Maintainers");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        centerVbox.getChildren().add(label);

        for (String maintainerUname : me.getMaintainers()) {
            Button btn = new Button(maintainerUname);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File(
                        // https://stackoverflow.com/questions/17228487/javafx-location-is-not-set-error-message
                        "../../fxml/manager_selection_maintainers.fxml"
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
        contributorUName = uname;
        managerUName.setText(contributorUName);
        me = (Manager) Contributor.getContributor(uname);
        showProjects();
    }
}