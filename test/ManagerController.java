import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import com.issue_tracker.*;

public class ManagerController extends MaintainerController {
    private String contributorUName;
    private Manager me;
    private SaveOrRetrieve<Manager> sr = new SaveOrRetrieve<>();

    @FXML
    private Label managerUName;
    
    @FXML
    private Button projectsButton;

    @FXML
    private Button maintainersButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane managerCenter;

    @FXML
    private VBox centerVbox;

    @FXML
    public void showProjects(){
        centerVbox.getChildren().clear();
        Label label = new Label("Projects");
        label.setMaxWidth(Double.MAX_VALUE);
        centerVbox.getChildren().add(label);
        for (Project p : me.getAllProjects()) {
            Button btn = new Button(p.getProjectName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            centerVbox.getChildren().add(btn);
        }
    }

    public void showMaintainers() {
        centerVbox.getChildren().clear();
        Label label = new Label("Maintainers");
        label.setMaxWidth(Double.MAX_VALUE);
        centerVbox.getChildren().add(label);

        for (Maintainer m : me.getAllMaintainers()) {
            Button btn = new Button(m.getUName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            centerVbox.getChildren().add(btn);
        }
    }


    public void initialize(String uname) {
        contributorUName = uname;
        managerUName.setText(contributorUName);
        me = sr.retrieveThisObject(contributorUName);
        showProjects();
    }
}