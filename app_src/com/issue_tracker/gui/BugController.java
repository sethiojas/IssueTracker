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
import java.io.File;
import com.issue_tracker.Bug;

/**Show an individual bug 
*/

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
        /**Initialize class members
        *@param bugID               bug's unique id
        *@param bugTitle            Title of bug
        *@param bugDesc             Description of/Steps to reproduce bug
        *@param projectID           Unique id of project to which bug belongs
        *@param contributorUname    Uname of contributor
        *@param parentOfProject     Path of fxml file which invoked ProjectController 
        */
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
        /**Close a bug  
        *Equivalent to marking it as resolved
        */
        bugStatus.setText("Closed");
        closeBugButton.setDisable(true);
        Bug.closeBug(bugID);
    }

    @FXML
    public void goBack(ActionEvent event){
        /**Go back to previous Screen 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/project.fxml").toURI().toURL());
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