package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import java.io.IOException;
import com.issue_tracker.Admin;

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
        */
        if (role.equals("manager")){
            new Admin().createNewManager(username.getText(), password.getText());
        }
        else{
            new Admin().createNewMaintainer(username.getText(), password.getText());
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