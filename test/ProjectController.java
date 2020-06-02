import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import com.issue_tracker.*;


public class ProjectController {

    private String contributorUName;
    private Project project;

    @FXML
    private Label projectTitle;

    @FXML
    private VBox issueTitleVbox;

    @FXML
    private VBox maintainerVbox;

    @FXML
    private Button newBugButton;

    @FXML
    private Button backButton;

    @FXML
    public void addBug(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_bug.fxml"));
            GridPane root = loader.load();
            AddBugController cont = loader.getController();
            cont.initialize(project, contributorUName);
            newBugButton.getScene().setRoot(root);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("maintainer.fxml"));
            GridPane root = loader.load();
            MaintainerController cont = loader.getController();
            cont.initialize(contributorUName);
            cont.saveChangesToProject(project);
            backButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    public void initialize(Project proj, String uname){
        project = proj;
        contributorUName = uname;
        projectTitle.setText(project.getProjectName());

        for (String _name: project.getMaintainers()){
            Label name = new Label(_name);
            name.setMaxWidth(Double.MAX_VALUE);
            name.setAlignment(Pos.CENTER);
            maintainerVbox.getChildren().add(name);
        }

        for (Bug bug: project.getAllBugs()){
            Button btn = new Button("#" + bug.getId() + " " + bug.getTitle());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);

            btn.setOnAction(e -> {
                try{
                    String IdNum = btn.getText().split("\\s",2)[0];
                    int bugId = Integer.parseInt(IdNum.substring(1));
                    Bug thisBug = project.getBug(bugId);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bug.fxml"));
                    GridPane root = loader.load();
                    BugController cont = loader.getController();
                    cont.initialize(thisBug, project, contributorUName);
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep){
                    excep.printStackTrace();
                }
            });

            issueTitleVbox.getChildren().add(btn);
        }
    }
}
