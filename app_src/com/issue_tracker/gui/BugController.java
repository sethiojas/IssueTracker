package com.issue_tracker.gui;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import com.issue_tracker.Bug;

public class BugController {
    private int bugID;
    private int projectID;
    private String contributorUName;
    private String parentOfProject;

    @FXML
    private Label bugTitle;
    @FXML
    private Label bugId;
    @FXML
    private Label bugStatus;
    @FXML
    private TextArea bugDescription;
    @FXML
    private Button backButton;
    @FXML
    private Button closeBugButton;

    public void initialize(int bugID, String bugTitle, String bugDesc,
                           int projectID, String contributorUName, String parentOfProject){
        this.bugID = bugID;
        this.projectID = projectID;
        this.contributorUName = contributorUName;
        this.parentOfProject = parentOfProject;

        this.bugTitle.setText(bugTitle);
        bugId.setText("Issue id #" + bugID);
        bugDescription.setText(bugDesc);
    }

    @FXML
    public void closeBug(ActionEvent event){
        bugStatus.setText("Closed");
        closeBugButton.setDisable(true);
        Bug.closeBug(bugID);
    }

    @FXML
    public void goBack(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/project.fxml"));
            BorderPane root = loader.load();
            ProjectController cont = loader.getController();
            cont.initialize(projectID, contributorUName, parentOfProject);
            backButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }
}