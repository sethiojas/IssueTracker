package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.io.File;
import com.issue_tracker.Maintainer;
import com.issue_tracker.Contributor;
import java.util.Map;
import java.util.HashMap;

public class MaintainerController implements HasProjects{
    
    @FXML
    private Label uNameLabel;
    
    @FXML
    private Label managerLabel;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button logoutButton;

    @FXML
    public void logout(ActionEvent event){
        try{
            GridPane root = FXMLLoader.load(new File("../../fxml/login.fxml").toURI().toURL());
            logoutButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    @Override
    public void initialize(String uname){
        Maintainer me = (Maintainer) Contributor.getContributor(uname);

        uNameLabel.setText(uname);
        managerLabel.setText("Manager: " + me.getManager());

        for (Map.Entry<String, Integer> entry: me.getProjects().entrySet()){
            Button btn = new Button(entry.getKey());
            btn.setAlignment(Pos.BASELINE_LEFT);
            //or btn.setStyle("-fx-alignment: LEFT;");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(new File("../../fxml/project.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    ProjectController cont = loader.getController();
                    cont.initialize(entry.getValue(), uname, "../../fxml/maintainer.fxml");
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep){
                    excep.printStackTrace();
                    return;
                }
            });
            projectsVbox.getChildren().add(btn);
        }
    }
}