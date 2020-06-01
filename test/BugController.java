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
    String contributorUName;
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

    public void initialize(Bug bug, Project proj, String uname){
        this.project = proj;
        this.contributorUName = uname;
        this.thisBugId = bug.getId();
        bugTitle.setText(bug.getTitle());
        bugId.setText("Issue id #" + thisBugId);
        bugDescription.setText(bug.getDescription());
    }

    @FXML
    public void closeBug(ActionEvent event){
        bugStatus.setText("Closed");
        closeBugButton.setDisable(true);
        project.closeBug(thisBugId);
    }

    @FXML
    public void goBack(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
            BorderPane root = loader.load();
            ProjectController cont = loader.getController();
            cont.initialize(project, contributorUName);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Project");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }
}