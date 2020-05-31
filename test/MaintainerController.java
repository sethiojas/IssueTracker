import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import com.issue_tracker.*;

public class MaintainerController {
    @FXML
    private Label uNameLabel;
    
    @FXML
    private Label managerLabel;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button logoutButton;

    public void initialize(String uname){
        SaveOrRetrieve<Maintainer> sr = new SaveOrRetrieve<>();
        Maintainer me = sr.retrieveThisObject(uname);

        uNameLabel.setText(uname);
        managerLabel.setText("Manager: " + me.getManager());

        for (Project p: me.getProjects()){
            Button btn = new Button(p.getProjectName());
            btn.setAlignment(Pos.BASELINE_LEFT);
            //or btn.setStyle("-fx-alignment: LEFT;");
            btn.setMaxWidth(Double.MAX_VALUE);
            projectsVbox.getChildren().add(btn);
        }
    }
}