import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import com.issue_tracker.*;


public class ProjectController {
    @FXML
    private Label projectTitle;

    @FXML
    private VBox issueTitleVbox;

    @FXML
    private VBox maintainerVbox;

    @FXML
    private Button newBugButton;

    public void initialize(Project proj){
        projectTitle.setText(proj.getProjectName());

        for (String uname: proj.getMaintainers()){
            Label name = new Label(uname);
            name.setMaxWidth(Double.MAX_VALUE);
            name.setAlignment(Pos.CENTER);
            maintainerVbox.getChildren().add(name);
        }

        for (Bug bug: proj.getAllBugs()){
            Button btn = new Button(bug.getTitle());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            issueTitleVbox.getChildren().add(btn);
        }
    }
}
