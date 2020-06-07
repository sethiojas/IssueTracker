package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.io.File;
import com.issue_tracker.Bug;

public class AddBugController {
    private int projectID;
    private String contributorUName;
    private String parentOfProject;

    @FXML
    private TextField bugTitle;

    @FXML
    private TextArea bugDescription;

    @FXML
    private Button submitButton;

    @FXML
    public void submit(ActionEvent event){
        new Bug(bugTitle.getText(), bugDescription.getText(), projectID);
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/project.fxml").toURI().toURL());
            BorderPane root = loader.load();
            ProjectController cont = loader.getController();
            cont.initialize(projectID, contributorUName, parentOfProject);
            submitButton.getScene().setRoot(root);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void initialize(int projectID, String contributorUName, String parentOfProject){
        this.projectID = projectID;
        this.contributorUName = contributorUName;
        this.parentOfProject = parentOfProject;
    }
}