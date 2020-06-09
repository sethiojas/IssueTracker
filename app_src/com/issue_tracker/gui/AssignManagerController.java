package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import com.issue_tracker.Admin;
import javafx.stage.Stage;

/**Class handles assigning of manager to an on-bench
*maintainer 
*/

public class AssignManagerController {
    private String adminName;

    @FXML
    private Label maintainerName;

    @FXML
    private VBox managerVbox;

    public void initialize(String adminName, String maintainerName){
        /**Initialize class member and call showManagers 
        *@param adminName       Uname of admin
        *@param maintainerName  Uname of maintainer
        */
        this.adminName = adminName;
        this.maintainerName.setText(maintainerName);
        showManagers(maintainerName);
    }

    public void showManagers(String maintainerName){
        /**Shows managers which can be assigned to maintainer
        *@param maintainerName  Uname of maintainer 
        */
        Admin admin = new Admin();

        // Do not assign any manager and go back button
        Button none = new Button("None");
        none.setMaxWidth(Double.MAX_VALUE);
        none.setAlignment(Pos.BASELINE_LEFT);
        none.setOnAction(e -> {
            Stage stage = (Stage) managerVbox.getScene().getWindow();
            stage.close();
        });
        managerVbox.getChildren().add(none);

        // Managers which can be assigned
        for (String manager: admin.getAllManagers()){
            Button btn = new Button(manager);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            btn.setOnAction(e -> {
                admin.assignManager(btn.getText(), maintainerName);
                Stage stage = (Stage) btn.getScene().getWindow();
                stage.close();
            });
            managerVbox.getChildren().add(btn);
        }
    }
}