import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.io.IOException;
import com.issue_tracker.*;

public class ManagerController extends MaintainerController implements Contributor {
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

    @Override
    public void saveChangesToProject(Project proj) {
        me.updateProject(proj);
        sr.updateThisObject(me);
    }

    @FXML
    public void showProjects(){
        centerVbox.getChildren().clear();
        Label label = new Label("Projects");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        centerVbox.getChildren().add(label);
        for (Project p : me.getAllProjects()) {
            Button btn = new Button(p.getProjectName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            btn.setOnAction(e -> {
                try{
                    Project project = me.getProjectByTitle(btn.getText());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
                    BorderPane root = loader.load();
                    ProjectController cont = loader.getController();
                    cont.initialize(project, contributorUName, "manager.fxml");
                    btn.getScene().setRoot(root);
                }catch(IOException excep) {
                    excep.printStackTrace();
                }
            });
            centerVbox.getChildren().add(btn);
        }
    }

    public void showMaintainers() {
        centerVbox.getChildren().clear();
        Label label = new Label("Maintainers");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        centerVbox.getChildren().add(label);

        for (Maintainer m : me.getAllMaintainers()) {
            Button btn = new Button(m.getUName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            btn.setOnAction(e -> {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        // https://stackoverflow.com/questions/17228487/javafx-location-is-not-set-error-message
                        "manager_selection_maintainers.fxml"
                        )
                    );
                    GridPane root = loader.load();
                    MaintainerDetailsController cont = loader.getController();
                    cont.initialize(me.getMaintainer(btn.getText()), contributorUName);
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep) {
                    excep.printStackTrace();
                }
            });
            centerVbox.getChildren().add(btn);
        }
    }

    @Override
    public void initialize(String uname) {
        contributorUName = uname;
        managerUName.setText(contributorUName);
        me = sr.retrieveThisObject(contributorUName);
        showProjects();
    }
}