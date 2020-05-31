import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class LoginController {
    // https://stackoverflow.com/questions/44010909/using-initialize-method-in-a-controller-in-fxml
    // https://stackoverflow.com/a/35300880 <- URL, ResourceBundle
    // https://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
    
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