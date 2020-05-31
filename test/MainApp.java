
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Window;


public class MainApp extends Application {
    private Stage stage;
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    void checkCredentials(ActionEvent event){
        String uname = username.getText();
        String passwd = password.getText();

        String res = CheckCreds.check(uname, passwd);
        if (res == null){
            Window owner = loginButton.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Credentials",
                                    "Please check the entered Username and Password");
        }

        System.out.print("Success " + uname + " | " + passwd + " | " + res);
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        GridPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("IssueTracker Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
