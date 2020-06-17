package com.issue_tracker.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import java.io.IOException;
import java.io.File;
import java.lang.StringBuilder;
import java.util.List;
import java.util.LinkedList;
import com.issue_tracker.Contributor;
import com.issue_tracker.Manager;
import javafx.scene.control.Alert;

/**Class handles creation of new project 
*Controller for new_project.fxml
*/

public class NewProjectController {
    private String managerUname;
    private Manager me;
    private List<String> maintainers = new LinkedList<>();

    @FXML
    private TextField titleField;

    @FXML
    private TextArea maintainersField;

    @FXML
    private Button cancelButton;

    @FXML
    private VBox displayVbox;

    @FXML
    public void submit() {
        /**Submit details, create project and go back
        *to previous screen 
        */

        // Show alert if title is blank
        String title = titleField.getText();
        if(title.length() < 1){
            AlertHelper.showAlert(Alert.AlertType.WARNING, titleField.getScene().getWindow()
                                , "Empty Title"
                                , "Title cannot be empty!");
            return;
        }

        me.createProject(title);
        me.addMaintainersToProject(title, maintainers);
        // to go back to previous screen
        cancel();
    }

    @FXML
    public void cancel(){
        /**Go back to previous scene without making
        *any changes 
        */
        try{
            FXMLLoader loader = new FXMLLoader(new File("../fxml/manager.fxml").toURI().toURL());
            Parent root = loader.load();
            ManagerController cont = loader.getController();
            cont.initialize(managerUname);
            cancelButton.getScene().setRoot(root);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void displayMaintainers(){
        /**Display all maintainer assigned to manager
        *so that they can be added to project as per
        *manager's choice 
        */
        List<String> maintainers = me.getMaintainers();
        if (maintainers.size() == 0) {
            maintainersField.setText("No maintainers to add");
            return;
        }    
        for (String name : maintainers) {
            HBox row = maintainerRow(name);
            displayVbox.getChildren().add(row);
        }
    }

    public HBox maintainerRow(String name) {
        /**Row contain maintainer name and buttons to either assign
        *or remove maintainer from the list of Maintainer of that
        *project 
        *@param name    Uname of maintainer
        *@return        HBox containg buttons
        */
        HBox root = new HBox();
        root.setSpacing(5);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);

        Button btn = new Button(name);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Button add = new Button("+");
        add.setOnAction(e -> {
            maintainers.add(name);
            maintainersField.setText(ListToString());
            add.setDisable(true);
        });

        Button remove = new Button("-");
        remove.setOnAction(e -> {
            maintainers.remove(name);
            maintainersField.setText(ListToString());
            add.setDisable(false);
        });

        root.getChildren().addAll(btn, add, remove);
        return root;
    }

    public String ListToString(){
        /**Turn the list of currently selected maintainers into
        *a single string 
        */
        StringBuilder sb = new StringBuilder();
        for (String item: maintainers){
            sb.append(item);
            sb.append(", ");
        }
        return sb.toString();
    }

    public void initialize(String uname) {
        /**Initialize class members and display maintainers 
        *which can be added to project
        */
        managerUname = uname;
        me = (Manager) Contributor.getContributor(managerUname);
        displayMaintainers();
    }
}
