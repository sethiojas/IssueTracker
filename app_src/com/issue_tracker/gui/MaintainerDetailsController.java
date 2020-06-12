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
import com.issue_tracker.Manager;
import java.util.ArrayList;
import java.util.List;

/**Show Details about the maintainer which is assigned
*to manager
*/

public class MaintainerDetailsController {
    private String managerUname;
    private String unameMaintainer;
    
    @FXML
    private Label maintainerUname;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button backButton;

    @FXML
    public void goBack() {
        /**Go back to previous screen 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/manager.fxml").toURI().toURL());
            BorderPane root = loader.load();
            ManagerController cont = loader.getController();
            cont.initialize(managerUname);
            cont.showMaintainers();
            backButton.getScene().setRoot(root);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(String unameMaintainer, String uname) {
        /**Initialize class members and
        *show all the projects assigned to maintainer(default)
        *@param unameMaintainer    Uname of maintainer whose details are to be shown
        *@param uname              Uname of manager 
        */
        managerUname = uname;
        this.unameMaintainer = unameMaintainer;
        maintainerUname.setText(unameMaintainer);
        showAssignedProjects();        
    }

    public void showAssignedProjects(){
        /**Show projects assigned to a maintainer 
        */
        projectsVbox.getChildren().clear();
        Maintainer maintainer = (Maintainer) Contributor.getContributor(unameMaintainer);
        for (String projectName : maintainer.getProjects().keySet()) {
            HBox row = assignedProjectsMenuRow(projectName);
            projectsVbox.getChildren().add(row);
        }
    }

    private HBox assignedProjectsMenuRow(String projectName){
        /**Return a row consisting of a project and buttons corresponding
        *to actions which can be performed on that project by the manager 
        *@param projectName     Name/title of the project
        */
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

        // Button to remove maintainer from project
        Button remove = new Button("-");
        remove.setOnAction(e ->{
            Manager manager = (Manager) Contributor.getContributor(managerUname);
            List<String> maintainersToRemove = new ArrayList<>();
            maintainersToRemove.add(unameMaintainer);
            manager.removeMaintainersFromProject(projectName ,maintainersToRemove);
            showAssignedProjects();
        });
        root.getChildren().addAll(btn, remove);
        return root;
    }
}