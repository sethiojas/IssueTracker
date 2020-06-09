package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.IOException;

/**Controller for login.fxml 
*Retrieves credentials from users and upon
*successful authentication open appropriate
*screen according to role associated with account 
*/

public class LoginController {
    // https://stackoverflow.com/questions/44010909/using-initialize-method-in-a-controller-in-fxml
    // https://stackoverflow.com/a/35300880 <- URL, ResourceBundle
    // https://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
    
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    void checkCredentials(ActionEvent event) {
        /**Authenticates entered credentials.
        *Upon successful authentication load
        *the corresponding scene.
        */
        String uname = username.getText();
        String passwd = password.getText();

        String res = CheckCreds.check(uname, passwd);
        if (res == null){
            Window owner = loginButton.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Credentials",
                                    "Please check the entered Credentials");
            return;
        }
        try{
            switch(res){
                case "admin": {
                    FXMLLoader loader = new FXMLLoader(new File("../fxml/admin.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    AdminController cont = loader.getController();
                    cont.initialize(uname);
                    loginButton.getScene().setRoot(root);
                    break;
                }
                case "manager": {
                    FXMLLoader loader = new FXMLLoader(new File("../fxml/manager.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    ManagerController cont = loader.getController();
                    cont.initialize(uname);
                    loginButton.getScene().setRoot(root);
                    break;
                }

                case "maintainer":{ 
                    FXMLLoader loader = new FXMLLoader(new File("../fxml/maintainer.fxml").toURI().toURL());
                    BorderPane root = loader.load();
                    // https://stackoverflow.com/questions/23461148/fxmlloader-getcontroller-returns-null
                    MaintainerController cont = loader.getController();
                    cont.initialize(uname);
                    // https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
                    // https://stackoverflow.com/questions/39985414/change-scene-without-resize-window-in-javafx
                    loginButton.getScene().setRoot(root);
                    break;
                }
            } 
        }catch(IOException e){
            e.printStackTrace();
        }       
    }
}