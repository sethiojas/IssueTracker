import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    public void goBack(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("maintainer.fxml"));
            GridPane root = loader.load();
            MaintainerController cont = loader.getController();
            cont.initialize(contributorUName);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Maintainer");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    public void initialize(Project proj, String uname){
        contributorUName = uname;
        projectTitle.setText(proj.getProjectName());

        for (String _name: proj.getMaintainers()){
            Label name = new Label(_name);
            name.setMaxWidth(Double.MAX_VALUE);
            name.setAlignment(Pos.CENTER);
            maintainerVbox.getChildren().add(name);
        }

        for (Bug bug: proj.getAllBugs()){
            Button btn = new Button("#" + bug.getId() + " " + bug.getTitle());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);

            btn.setOnAction(e -> {
                try{
                    String IdNum = btn.getText().split("\\s",2)[0];
                    int bugId = Integer.parseInt(IdNum.substring(1));
                    Bug thisBug = proj.getBug(bugId);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bug.fxml"));
                    GridPane root = loader.load();
                    BugController cont = loader.getController();
                    cont.initialize(thisBug, proj, contributorUName);

                    Stage stage = (Stage) btn.getScene().getWindow();
                    stage.setTitle("Bug");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch(IOException excep){
                    excep.printStackTrace();
                }
            });

            issueTitleVbox.getChildren().add(btn);
        }
    }
}
