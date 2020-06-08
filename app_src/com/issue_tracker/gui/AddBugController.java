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
import com.issue_tracker.Project;

/**Controller Class for  add_bug.fxml
*Class handles creation of bugs
*Bugs can be created by Contributors i.e. Manager and Maintainers
*Class members
*projectID        : Project id of the project to which bug belongs.
*contributorUName : Uname of the contributor.
*parentOfProject  : FXML file whose controler invoked the ProjectController.
*
*FXML member
*bugTitle       : Displays title of bug.
*bugDescription : Shows description of bug.
*submitButton   : Button to submit new bug
*/

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
        /**Event handler of submitButton
        *Creates a new bug and switches the Scene back to Project
        *@param event ActionEvent type
        */
        Project project = Project.getProject(projectID);
        project.createNewBug(bugTitle.getText(), bugDescription.getText());
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
        /**Initializes the class members
        *
        *@param projectID           The project id of project
        *@param contributorUName    The name of the contributor which is creating the bug
        *@param parentOfProject     Path of FXML file whose controller invoked the ProjectController
        */
        this.projectID = projectID;
        this.contributorUName = contributorUName;
        this.parentOfProject = parentOfProject;
    }
}