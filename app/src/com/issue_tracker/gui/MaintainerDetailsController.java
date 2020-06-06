package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.io.File;
import com.issue_tracker.Maintainer;
import com.issue_tracker.Contributor;

public class MaintainerDetailsController {
    private String contributorUName;
    
    @FXML
    private Label maintainerUname;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button backButton;

    @FXML
    public void goBack() {
        try{
            FXMLLoader loader = new FXMLLoader(new File("manager.fxml").toURI().toURL());
            BorderPane root = loader.load();
            ManagerController cont = loader.getController();
            cont.initialize(contributorUName);
            cont.showMaintainers();
            backButton.getScene().setRoot(root);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(String unameMaintainer, String uname) {
        contributorUName = uname;
        maintainerUname.setText(unameMaintainer);

        Maintainer maintainer = (Maintainer) Contributor.getContributor(unameMaintainer);
        for (String projectName : maintainer.getProjects().keySet()) {
            HBox row = assignedProjectsMenuRow(projectName);
            projectsVbox.getChildren().add(row);
        }
    }

    private HBox assignedProjectsMenuRow(String projectName){
        HBox root = new HBox();
        root.setSpacing(10);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        // https://stackoverflow.com/questions/19325351/how-to-set-hgrow-property-dynamically
        Button btn = new Button(projectName);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Button remove = new Button("-");
        // remove.setOnAction(e ->{

        // });
        root.getChildren().addAll(btn, remove);
        return root;
    }
}