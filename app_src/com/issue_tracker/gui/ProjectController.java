package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import java.io.File;
import com.issue_tracker.Project;
import java.util.HashMap;

/**Controller class for project.fxml 
*/

public class ProjectController {

    private String contributorUName;
    private int projectID;
    private String parentFXML;

    @FXML
    private Label projectTitle;

    @FXML
    private VBox issueTitleVbox;

    @FXML
    private VBox maintainerVbox;

    @FXML
    private Button newBugButton;

    @FXML
    private Button backButton;

    @FXML
    public void addBug(ActionEvent event){
        /**Create a new bug issue for currently selected
        *project 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/add_bug.fxml").toURI().toURL());
            GridPane root = loader.load();
            AddBugController cont = loader.getController();
            cont.initialize(projectID, contributorUName, parentFXML);
            newBugButton.getScene().setRoot(root);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack(ActionEvent event){
        /**Go back to previous screen 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File(parentFXML).toURI().toURL());
            Parent root = loader.load();
            HasProjects cont = loader.getController();
            cont.initialize(contributorUName);
            backButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    public void initialize(int projectID, String uname, String parentFXML){
        /**Initialize class members Display all the bugs associated with project 
        */
        this.projectID = projectID;
        this.parentFXML = parentFXML;
        contributorUName = uname;

        Project project = Project.getProject(projectID);
        projectTitle.setText(project.getProjectName());

        for (String _name: project.getMaintainers()){
            Label name = new Label(_name);
            name.setMaxWidth(Double.MAX_VALUE);
            name.setAlignment(Pos.CENTER);
            maintainerVbox.getChildren().add(name);
        }

        for (HashMap<String, String> bug: project.getAllBugs()){
            Button btn = new Button("#" + bug.get("id") + " " + bug.get("title"));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);

            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File("../fxml/bug.fxml").toURI().toURL());
                    GridPane root = loader.load();
                    BugController cont = loader.getController();
                    cont.initialize(Integer.parseInt(bug.get("id")), bug.get("title"),
                                    bug.get("desc"), projectID, contributorUName, parentFXML);
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep){
                    excep.printStackTrace();
                }
            });

            issueTitleVbox.getChildren().add(btn);
        }
    }
}
