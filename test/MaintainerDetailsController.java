import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import com.issue_tracker.*;
import java.io.IOException;

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
            Button btn = new Button(p.getProjectName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.BASELINE_LEFT);
            projectsVbox.getChildren().add(btn);
        }
    }
}