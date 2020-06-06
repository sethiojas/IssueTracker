import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import com.issue_tracker.*;

public class MaintainerDetailsController {
    private String contributorUName;
    
    @FXML
    private Label maintainerUname;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button backButton;

    @FXML
    public void goBack() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manager.fxml"));
            BorderPane root = loader.load();
            ManagerController cont = loader.getController();
            cont.initialize(contributorUName);
            cont.showMaintainers();
            backButton.getScene().setRoot(root);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(Maintainer maintainer, String uname) {
        contributorUName = uname;
        maintainerUname.setText(maintainer.getUName());
        for (Project p : maintainer.getProjects()) {
            HBox row = assignedProjectsMenuRow(p);
            projectsVbox.getChildren().add(row);
        }
    }

    private HBox assignedProjectsMenuRow(Project p){
        HBox root = new HBox();
        root.setSpacing(10);
        root.setMaxHeight(27);
        root.setMaxWidth(Double.MAX_VALUE);
        
        // https://stackoverflow.com/questions/19325351/how-to-set-hgrow-property-dynamically
        Button btn = new Button(p.getProjectName());
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);

        HBox.setHgrow(btn, Priority.ALWAYS);

        Button remove = new Button("-");
        // remove.setOnAction(e ->{

        // });
        root.getChildren().addAll(btn, remove);
        return root;
    }
}