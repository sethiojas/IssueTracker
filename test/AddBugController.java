import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.event.ActionEvent;
import com.issue_tracker.*;

public class AddBugController {
    private Project project;
    private String contributorUName;

    @FXML
    private TextField bugTitle;

    @FXML
    private TextArea bugDescription;

    @FXML
    private Button submitButton;

    @FXML
    public void submit(ActionEvent event){
        project.createNewBug(bugTitle.getText(), bugDescription.getText());
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
            BorderPane root = loader.load();
            ProjectController cont = loader.getController();
            cont.initialize(project, contributorUName);
            submitButton.getScene().setRoot(root);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void initialize(Project proj, String uname){
        project = proj;
        contributorUName = uname;
    }
}