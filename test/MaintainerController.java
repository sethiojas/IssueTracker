import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import com.issue_tracker.*;
import java.io.IOException;

public class MaintainerController implements Contributor {
    private SaveOrRetrieve<Maintainer> sr = new SaveOrRetrieve<>();
    private Maintainer me;

    @FXML
    private Label uNameLabel;
    
    @FXML
    private Label managerLabel;

    @FXML
    private VBox projectsVbox;

    @FXML
    private Button logoutButton;

    @FXML
    public void logout(ActionEvent event){
        try{
            GridPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
            logoutButton.getScene().setRoot(root);
        }
        catch(IOException excep){
            excep.printStackTrace();
        }
    }

    @Override
    public void saveChangesToProject(Project project){
        me.updateProject(project);
        sr.updateThisObject(me);
    }

    @Override
    public void initialize(String uname){
        me = sr.retrieveThisObject(uname);

        uNameLabel.setText(uname);
        managerLabel.setText("Manager: " + me.getManager());

        for (Project p: me.getProjects()){
            Button btn = new Button(p.getProjectName());
            btn.setAlignment(Pos.BASELINE_LEFT);
            //or btn.setStyle("-fx-alignment: LEFT;");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                try{
                    String projTitle = btn.getText();
                    Project proj = me.getProjectByTitle(projTitle);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
                    BorderPane root = loader.load();
                    ProjectController cont = loader.getController();
                    cont.initialize(proj, me.getUName(), "maintainer.fxml");
                    btn.getScene().setRoot(root);
                }
                catch(IOException excep){
                    excep.printStackTrace();
                    return;
                }
            });
            projectsVbox.getChildren().add(btn);
        }
    }
}