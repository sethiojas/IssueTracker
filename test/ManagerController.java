import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import com.issue_tracker.*;

public class ManagerController extends MaintainerController {
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


    public void initialize(String uname) {
        contributorUName = uname;
        managerUName.setText(contributorUName);
        me = sr.retrieveThisObject(contributorUName);
    }
}