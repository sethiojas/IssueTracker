package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import com.issue_tracker.Admin;
import javafx.stage.Stage;

public class AssignManagerController {
    private String adminName;

    @FXML
    private Label maintainerName;

    @FXML
    private VBox managerVbox;

    public void initialize(String adminName, String maintainerName){
        this.adminName = adminName;
        showManagers(maintainerName);
    }

    public void showManagers(String maintainerName){
        Admin admin = new Admin();

        Button none = new Button("None");
        none.setMaxWidth(Double.MAX_VALUE);
        none.setAlignment(Pos.BASELINE_LEFT);
        none.setOnAction(e -> {
            Stage stage = (Stage) managerVbox.getScene().getWindow();
            stage.close();
        });
        managerVbox.getChildren().add(none);

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