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
    private TextField currentPasswordField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField reEnterPasswordField;

    @FXML
    private Button okButton;

    @FXML
    public void okAction(){
        /**Event handler for ok button
        *Performs a series of check and then changes the password
        *associated with the account
        *checks include
        *   -fields should be non empyt
        *   -current password should be valid
        *   -new password should not be equal to old password
        *   -new password and re-entered password should match
        */
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String reEnterPassword = reEnterPasswordField.getText();

        Stage stage = (Stage) okButton.getScene().getWindow();

        String check = CheckCreds.check(uname, currentPassword);
        
        if (currentPassword.length() < 1 ||
            newPassword.length() < 1 ||
            reEnterPassword.length() < 1){
            AlertHelper.showAlert(Alert.AlertType.WARNING, stage, "Warning",
                                 "Required field(s) empty!");
            return;
        }
        else if(check == null){
            AlertHelper.showAlert(Alert.AlertType.WARNING, stage, "Warning",
                                 "Password for account is incorrect!");
            return;
        }
        else if (currentPassword.equals(newPassword)){
            AlertHelper.showAlert(Alert.AlertType.WARNING, stage, "Warning",
                                 "Old password can not be same as\nNew Password!");
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