package PopUpScreens;

import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class ControllerModifyPassword {
    private User user;

    @FXML
    private TextField tfpassword1;
    @FXML
    private TextField tfpassword2;
    @FXML
    private Label errorPwd;

    @FXML
    private Button btnModifyPassword;

    public void initialize(URL location, ResourceBundle resources) {}

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if (actionEvent.getSource() == btnModifyPassword) {
            if(modifypwd()){
                Stage primaryStage = (Stage) btnModifyPassword.getScene().getWindow();
                primaryStage.close();
            }
        }
    }

    private boolean modifypwd() throws InterruptedException, NoSuchAlgorithmException, IOException {
        String pwd1 = tfpassword1.getText();
        String pwd2 = tfpassword2.getText();

        if(pwd1.equals(pwd2)){
            user.modifyPassword(pwd1);
            return true;
        }

        tfpassword2.setStyle("-fx-border-color: #ff0000");
        errorPwd.setVisible(true);
        return false;
    }

    public void loadCreateWindow(User u) {
        user = u;
    }
}
