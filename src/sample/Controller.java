package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML
    private Button connectBtn;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void handleClicks(ActionEvent actionEvent) throws IOException, InterruptedException, NoSuchAlgorithmException {
        if(actionEvent.getSource() == connectBtn){

            Connexion connexion = new Connexion(username.getText(), password.getText());
            System.out.println(connexion.getPassword());
            int idUser = connexion.checkConnexion();

            if(idUser == 0){
                errorLabel.setVisible(true);
            } else {
                User userSession = new User(idUser);

                Stage mainStage = new Stage();

                Parent mainApp = FXMLLoader.load(getClass().getResource("../home/Home.fxml"));
                mainStage.setTitle("LinkService Backoffice");
                mainStage.setScene(new Scene(mainApp));
                mainStage.show();

                Stage primaryStage = (Stage) connectBtn.getScene().getWindow();
                primaryStage.close();

            }


        }
    }

}
