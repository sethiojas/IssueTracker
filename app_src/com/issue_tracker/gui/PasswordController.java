package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import com.issue_tracker.ChangePassword;
import com.issue_tracker.HashString;

/**This class handles change of password 
*/

public class PasswordController {
    private String uname;

    @FXML
    private TextField oldPasswordField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField reEnterPasswordField;

    @FXML
    private Button okButton;

    @FXML
    public void okAction(){
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String reEnterPassword = reEnterPasswordField.getText();

        Stage stage = (Stage) okButton.getScene().getWindow();

        if (oldPassword.equals(newPassword)){
            AlertHelper.showAlert(Alert.AlertType.WARNING, stage, "Warning",
                                 "Old password can not be same as New Password!");
            return;
        }else if (!newPassword.equals(reEnterPassword)){
            AlertHelper.showAlert(Alert.AlertType.WARNING, stage, "Warning",
                                 "Passwords do not match!");
            return;
        }
        newPassword = HashString.hash(newPassword);
        ChangePassword.change(uname, newPassword);
        stage.close();
    }

    public void initialize(String uname){
        /**Initialize the class variables
        *@param uname       Uname of maintainer
        */
        this.uname = uname;
    }
}