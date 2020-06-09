package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import java.io.IOException;
import com.issue_tracker.Admin;
import com.issue_tracker.HashString;
import com.issue_tracker.Contributor;
import javafx.scene.control.Alert;
import javafx.stage.Window;

/**Handles creation of Contributors (Manager/Maintainer) 
*/

public class AddContributorController {
    private String role;
    private String uname;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button cancelButton;

    @FXML
    public void submit(){
        /**Event handler for submit button 
        *Creates new Maintainer or Manager depending upon the role
        *shows alert if username is already taken
        */
        String uname = username.getText();
        String passwordString = password.getText();

        Window owner = username.getScene().getWindow();
        if(Contributor.getContributor(uname) != null || uname.equals("admin")){
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Username already taken",
                                 "This Username is already taken!");
            return;
        }else if(uname.length() < 1 || passwordString.length() < 1){
             AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Warning",
                                 "Required Field(s) empty!");
            return;
        }
        
        passwordString = HashString.hash(passwordString);

        if (role.equals("manager")){
            new Admin().createNewManager(uname, passwordString);
        }
        else{
            new Admin().createNewMaintainer(uname, passwordString);
        }
        // After creation go back to previous screen
        cancel();
    }

    @FXML
    public void cancel() {
        /**Event handler for cancel button
        *Takes user back to previous screen
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/admin.fxml").toURI().toURL());
            Parent root = loader.load();
            AdminController cont = loader.getController();
            cont.initialize(uname);
            if(role.equals("maintainer")){
                cont.showOnBench();
            }
            cancelButton.getScene().setRoot(root);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void initialize(String uname, String role) {
        /**Initialize class members
        *@param uname   Uname of admin
        *@param role    Specifies role of the newly created Contributor 
        */
        this.uname = uname;
        this.role = role;
    }
}