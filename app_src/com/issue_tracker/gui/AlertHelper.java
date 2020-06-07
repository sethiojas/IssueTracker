package com.issue_tracker.gui;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**Creates alert popup based on provided information 
*/
// https://www.callicoder.com/javafx-fxml-form-gui-tutorial/
public class AlertHelper {

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        /**Create and show alert
        *@param alertType   type of alert is to be shown
        *@param owner       the window on which alert is to be shown
        *@param title       title of the alert popup
        *@param message     message shown by the alert popup 
        */
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}