import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import com.issue_tracker.*;

public class BugController {
    Project project;
    int thisBugId;
    @FXML
    private Label bugTitle;
    @FXML
    private Label bugId;
    @FXML
    private Label bugStatus;
    @FXML
    private TextArea bugDescription;
    @FXML
    private Button backButton;
    @FXML
    private Button closeBugButton;

    public void initialize(Bug bug, Project proj){
        this.project = proj;
        this.thisBugId = bug.getId();
        bugTitle.setText(bug.getTitle());
        bugId.setText("Issue id #" + thisBugId);
        bugDescription.setText(bug.getDescription());
    }
}