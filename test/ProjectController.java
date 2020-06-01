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

    public void initialize(Project proj){
        projectTitle.setText(proj.getProjectName());

        for (String uname: proj.getMaintainers()){
            Label name = new Label(uname);
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
                    cont.initialize(thisBug, proj);

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
