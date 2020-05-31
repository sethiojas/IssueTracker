import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Window;
// import javafx.fxml.Initializable;
import com.issue_tracker.*;

public class LoginController{
    
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize(){}

    @FXML
    void checkCredentials(ActionEvent event) throws Exception{
        String uname = username.getText();
        String passwd = password.getText();

        String res = CheckCreds.check(uname, passwd);
        if (res == null){
            Window owner = loginButton.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Credentials",
                                    "Please check the entered Username and Password");
            return;
        }
        switch(res){
            // case "admin": sr = new SaveOrRetrieve<Admin>();break;
            // case "manager": sr = new SaveOrRetrieve<Manager>();
            //                 break
            // case "maintainer": sr = new SaveOrRetrieve<Maintainer>();
            //                    GridPane root = FXMLLoader.load(getClass().getResource("maintainer.fxml"));
            //                 // https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
            //                    Stage stage = (Stage) loginButton.getScene().getWindow();
            //                    stage.setTitle("Maintainer");
            //                    stage.setScene(new Scene(root));
            //                    stage.show();
            //                    break;
        }        
    }
}